package sandbox;

import reactor.core.publisher.Flux;

import java.util.Arrays;

public class Sandbox {

    public static void main(String[] args) {
        Flux<String> flux = Flux.fromIterable(Arrays.asList("a", "b", "c"));

        flux.subscribe(System.out::println);
        flux.subscribe(System.out::println);
    }

}
