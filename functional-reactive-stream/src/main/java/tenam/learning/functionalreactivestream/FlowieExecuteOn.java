package tenam.learning.functionalreactivestream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;

public class FlowieExecuteOn<T> extends Flowie<T> {

    private Publisher<T> source;

    private ExecutorService executor;


    public FlowieExecuteOn(Publisher<T> source, ExecutorService executor) {
        this.source = source;
        this.executor = executor;
    }

    @Override
    public void subscribe(Subscriber<? super T> s) {
        this.source.subscribe(new ExecuteOnSubscriber<>(s, this.executor));
    }


    ///////////////////////////////////////////////////////////////////////////


    static class ExecuteOnSubscriber<T> implements Subscriber<T>, Subscription {

        private Subscriber<T> actualSubscriber;

        private ExecutorService executor;

        private Subscription upstreamSubscription;


        public ExecuteOnSubscriber(Subscriber<T> actualSubscriber, ExecutorService executor) {
            this.actualSubscriber = actualSubscriber;
            this.executor = executor;
        }


        @Override
        public void onSubscribe(Subscription s) {
            this.upstreamSubscription = s;
            this.actualSubscriber.onSubscribe(this);
        }

        @Override
        public void onNext(T t) {
            this.executor.submit(() -> this.actualSubscriber.onNext(t));
        }

        @Override
        public void onError(Throwable t) {
            this.executor.submit(() -> this.actualSubscriber.onError(t));
        }

        @Override
        public void onComplete() {
            this.executor.submit(() -> this.actualSubscriber.onComplete());
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
