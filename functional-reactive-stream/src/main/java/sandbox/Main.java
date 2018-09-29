package sandbox;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import tenam.learning.functionalreactivestream.Flowie;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static void main(String[] args) {
//        subscriberExample();
//        mapExample();
//        flatMapExample();
//        executeOnExample();
        futureTest();
    }

    static void futureTest() {
        ExecutorService es1 = Executors.newFixedThreadPool(2,
                new ThreadFactoryBuilder().setNameFormat("supply-%d").build());

        ExecutorService es2 = Executors.newFixedThreadPool(2,
                new ThreadFactoryBuilder().setNameFormat("apply-compose-%d").build());

        CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < 10; ++i);
            out("genearte abc");
            return "abc";
            }, es1)
                .thenApplyAsync(s -> {
                    out("thenApply(): " + s);
                    return s;

                }, es2);
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

        ExecutorService executor = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("subscriber-handling-%d")
                        .build());

        Flowie.fromIterable(Collections.nCopies(10000000, new Object()))
                .executeOn(executor)
                .subscribe(new Subscriber<Object>() {

                    private AtomicLong counter = new AtomicLong(0L);

                    private Subscription subscription;


                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        s.request(10);
                        out("onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        out("onNext: " + this.counter.incrementAndGet());
                        this.subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        out("onError");
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        out("onComplete");
                    }
                });

    }

    static void out(Object msg) {
        System.out.println(String.format("[%s] %s",
                Thread.currentThread().getName(), msg));
    }
}
