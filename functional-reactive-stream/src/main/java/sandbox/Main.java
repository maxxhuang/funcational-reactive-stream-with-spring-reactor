package sandbox;

import com.google.common.collect.Range;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import tenam.learning.functionalreactivestream.Flowie;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static void main(String[] args) {
        subscriberExample();
        mapExample();
        flatMapExample();
    }

    static void subscriberExample() {
        Flowie.fromIterable(Arrays.asList("a", "b", "c")).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    static void mapExample() {
        Flowie.fromIterable(Arrays.asList("a", "b", "c"))
                .map(s -> (int) s.charAt(0))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer n) {
                        System.out.println(n);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    static void flatMapExample() {
        Flowie.fromIterable(Arrays.asList(1, 2, 3))
                .flatMap(n -> Flowie.fromIterable(Collections.nCopies(n, "" + n)))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.print(s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    static void executeOnExample() {
        Executors.newFixedThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("flowie-subscriber-handling");
                return t;
            }
        });

        Flowie.fromIterable(Collections.nCopies(10000000, new Object()));

    }
}
