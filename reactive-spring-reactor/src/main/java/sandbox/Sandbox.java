package sandbox;

import reactor.core.publisher.Flux;
import tenam.learning.reactivespringreactor.devicesimulator.generator.MacGenerator;

import java.util.Arrays;

public class Sandbox {

    public static void main(String[] args) {
        new MacGenerator("AA", "BB").generate(260).forEach(System.out::println);
    }

}
