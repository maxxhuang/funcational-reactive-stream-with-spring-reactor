package tenam.learning.reactivespringreactor.devicesimulator.generator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class SetValueGenerator<V> extends AbstractValueGenerator<Set<V>> {

    private ValueGenerator<V> componentGenerator;

    private int size;

    private boolean isRandom = false;

    private Random random = new Random(System.currentTimeMillis());


    public SetValueGenerator(ValueGenerator<V> componentGenerator, int size) {
        this.componentGenerator = componentGenerator;
        this.size = size;
    }


    @Override
    public Set<V> generate() {
        int setSize = determineSetSize();
        return new HashSet<>(this.componentGenerator.generate(setSize));
    }


    public int getSize() {
        return size;
    }


    public SetValueGenerator<V> setSize(int size) {
        this.size = size;
        return this;
    }


    public boolean isRandom() {
        return isRandom;
    }


    public SetValueGenerator<V> setRandom(boolean isRandom) {
        this.isRandom = isRandom;
        return this;
    }

    private int determineSetSize() {
        if (this.isRandom) {
            return this.random.nextInt(this.size + 1);
        } else {
            return this.size;
        }
    }
}
