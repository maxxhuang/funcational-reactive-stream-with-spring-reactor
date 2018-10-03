package tenam.learning.functionalreactivestream;

import org.reactivestreams.Publisher;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

abstract public class Flowie<T> implements Publisher<T> {

    public static <T> Flowie<T> fromIterable(Iterable<T> iterable) {
        return new FlowieIterable<>(iterable);
    }

    public <R> Flowie<R> map(Function<? super T, ? extends R> mapper) {
        return new FlowieMap<>(this, mapper);
    }

    public <R> Flowie<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return new FlowieNonCompliantSynchronousFlatMap<T, R>(this, mapper);
    }

    public Flowie<T> executeOn(ExecutorService executor) {
        return new FlowieExecuteOn<T>(this, executor);
    }

}
