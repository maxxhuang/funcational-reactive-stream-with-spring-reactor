package tenam.learning.functionalreactivestream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.*;
import java.util.function.Function;

/**
 * This implementation DOES NOT comply with Reactive Streams. It does not take care of
 * the situation where elements from sub-streams emitted asynchronously.
 *
 * The compliant implementation is complicated and usually needs one or more queues
 * to store un-consumed elements emitted from sub-streams.
 *
 * This non-compliant implementation only serves the purpose of demonstrating the
 * advantage of making reactive streams functional.
 */
public class FlowieNonCompliantSynchronousFlatMap<T, R> extends Flowie<R> {

    private Publisher<T> source;

    private Function<? super T, ? extends Publisher<? extends R>> mapper;


    public FlowieNonCompliantSynchronousFlatMap(Publisher<T> source, Function<? super T, ? extends Publisher<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Subscriber<? super R> s) {
        this.source.subscribe(new SynchronousNonThreadSafeFlapMapSubscriber<>(s, this.mapper));
    }


    ///////////////////////////////////////////////////////////////////////////


    static class SynchronousNonThreadSafeFlapMapSubscriber<T, R> implements Subscriber<T>, Subscription {

        private Subscriber<R> actualSubscriber;

        private Function<? super T, ? extends Publisher<? extends R>> mapper;

        private Subscription subscription;

        private InnerPublisherIterator<R> currentInnerPublisher;

        private boolean isDrained = false;
        private boolean isDone = false;


        public SynchronousNonThreadSafeFlapMapSubscriber(Subscriber<? extends R> actualSubscriber,
                                                         Function<? super T, ? extends Publisher<? extends R>> mapper) {
            this.actualSubscriber = (Subscriber<R>) actualSubscriber;
            this.mapper = mapper;
        }


        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.subscription.request(1);
            this.actualSubscriber.onSubscribe(this);
        }

        @Override
        public void onNext(T t) {
            try {
                Publisher<? extends R> innerPublisher = this.mapper.apply(t);
                this.currentInnerPublisher = new InnerPublisherIterator<>();
                innerPublisher.subscribe(this.currentInnerPublisher);
            } catch (Throwable e) {
                onError(e);
            }
        }

        @Override
        public void onError(Throwable t) {
            this.isDone = true;
            this.actualSubscriber.onError(t);
        }

        @Override
        public void onComplete() {
            this.isDrained = true;
        }

        @Override
        public void request(long n) {
            if (this.currentInnerPublisher == null) {
                this.isDone = true;
                this.actualSubscriber.onComplete();
            }

            if (this.isDone) {
                return;
            }

            long emitted = 0;
            for (; emitted < n && this.currentInnerPublisher.hasNext(); ++emitted) {
                try {
                    R r = this.currentInnerPublisher.next();
                    this.actualSubscriber.onNext(r);
                } catch (Throwable e) {
                    this.isDone = true;
                    this.actualSubscriber.onError(e);
                }
            }

            if (emitted < n) {
                this.subscription.request(1);

                if (!this.currentInnerPublisher.hasNext()) {
                    this.isDone = true;
                    this.actualSubscriber.onComplete();
                }

                request(n - emitted);
            }
        }

        @Override
        public void cancel() {

        }
    }


    static class InnerPublisherIterator<T> implements Iterator<T>, Subscriber<T> {

        private Subscription subscription;

        private Queue<T> elementQueue = new ArrayDeque<>();

        private boolean isDrained = false;

        private Throwable error = null;


        @Override
        public boolean hasNext() {
            return !this.elementQueue.isEmpty();
        }

        @Override
        public T next() {
            if (!this.isDrained && this.error == null) {
                this.subscription.request(1);
            }

            if (this.elementQueue.isEmpty() && this.error != null) {
                throw new RuntimeException(this.error);
            }

            return this.elementQueue.remove();
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            s.request(1);
        }

        @Override
        public void onNext(T t) {
            this.elementQueue.offer(t);
        }

        @Override
        public void onError(Throwable t) {
            this.error = t;
        }

        @Override
        public void onComplete() {
            this.isDrained = true;
        }
    }

}
