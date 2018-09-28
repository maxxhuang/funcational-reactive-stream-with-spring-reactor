package tenam.learning.functionalreactivestream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Objects;
import java.util.function.Function;

public class FlowieMap<T, R> extends Flowie<R> {

    private Publisher<T> source;

    private Function<? super T, ? extends R> mapper;


    public FlowieMap(Publisher<T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }


    @Override
    public void subscribe(Subscriber<? super R> s) {
        this.source.subscribe(new MapSubscriber<>(s, this.mapper));
    }


    ///////////////////////////////////////////////////////////////////////////


    static class MapSubscriber<T, R> implements Subscriber<T>, Subscription {

        private Subscriber<? super R> actualSubscriber;

        private Function<? super T, ? extends R> mapper;

        private Subscription upstreamSubscription;


        public MapSubscriber(Subscriber<? super R> actualSubscriber, Function<? super T, ? extends R> mapper) {
            this.actualSubscriber = actualSubscriber;
            this.mapper = mapper;
        }


        @Override
        public void onSubscribe(Subscription s) {
            this.upstreamSubscription = s;
            this.actualSubscriber.onSubscribe(this);
        }

        @Override
        public void onNext(T t) {

            R r = null;

            try {
                r = Objects.requireNonNull(this.mapper.apply(t));
            } catch (Throwable e) {
                onError(e);
            }

            this.actualSubscriber.onNext(r);
        }

        @Override
        public void onError(Throwable t) {
            this.actualSubscriber.onError(t);
        }

        @Override
        public void onComplete() {
            this.actualSubscriber.onComplete();
        }

        @Override
        public void request(long n) {
            this.upstreamSubscription.request(n);
        }

        @Override
        public void cancel() {
            this.upstreamSubscription.cancel();
        }
    }
}
