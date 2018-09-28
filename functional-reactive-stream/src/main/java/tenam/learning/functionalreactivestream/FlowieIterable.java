package tenam.learning.functionalreactivestream;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FlowieIterable<T> extends Flowie<T> {

    private Iterable<T> iterable;


    public FlowieIterable(Iterable<T> iterable) {
        this.iterable = iterable;
    }


    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        try {
            Iterator<T> iterator = this.iterable.iterator();
            subscriber.onSubscribe(new IterableSubscription<>(subscriber, iterator));
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }


    ///////////////////////////////////////////////////////////////////////////


    static class IterableSubscription<T> implements Subscription {

        private Subscriber<? super T> subscriber;

        private Iterator<? extends T> iterator;

        private boolean cancelled = false;

        /**
         * pending request
         *
         * The updates to the pending request may come from other threads via {@link Subscription#request(long)}.
         *
         * Use AtomicLong to make sure the update and comparison of pending request counter is conducted atomically.
         *
         */
        private AtomicLong requested = new AtomicLong(0L);


        public IterableSubscription(Subscriber<? super T> subscriber, Iterator<? extends T> iterator) {
            this.iterator = iterator;
            this.subscriber = subscriber;
        }


        @Override
        public void request(long n) {
            long updatedRequested = addRequestAndReturnCurrent(n);

            if (updatedRequested == 0L) {
                doEmit(n);
            }
        }

        private void doEmit(long n) {

            long emitted = 0L;

            while (true) {


                while (emitted != n) {
                    T t;

                    try {
                        t = Objects.requireNonNull(this.iterator.next(), "The iterator returned a null value");
                    } catch (Throwable ex) {
                        this.subscriber.onError(ex);
                        return;
                    }

                    if (cancelled) return;

                    this.subscriber.onNext(t);

                    if (cancelled) return;

                    boolean hasMore;

                    try {
                        hasMore = this.iterator.hasNext();
                    } catch (Throwable ex) {
                        this.subscriber.onError(ex);
                        return;
                    }

                    if (cancelled) return;

                    if (!hasMore) {
                        this.subscriber.onComplete();
                        return;
                    }

                    emitted++;
                } // end of while (emitted != n)...

                n = this.requested.get();

                if (n == emitted) {
                    n = this.requested.addAndGet(-emitted);

                    // no more request
                    if (n == 0L) return;

                    // we have other requests coming in
                    emitted = 0L;
                }

            } // end of while (true)...
        }

        @Override
        public void cancel() {
            this.cancelled = true;
        }

        private long addRequestAndReturnCurrent(long toAdd) {

            long currentRequested, updatedRequested;

            for (;;) {
                currentRequested = this.requested.get();

                if (currentRequested == Long.MAX_VALUE) {
                    return Long.MAX_VALUE;
                }

                updatedRequested = determineFinalRequested(currentRequested, toAdd);

                if (this.requested.compareAndSet(currentRequested, updatedRequested)) {
                    return currentRequested;
                }
            }
        }

        private long determineFinalRequested(long currentRequested, long toAdd) {
            long result = currentRequested + toAdd;

            if (result < 0L) {
                return Long.MAX_VALUE;
            }

            return result;
        }

    }

}
