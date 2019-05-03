package tenam.learning.reactivespringreactor.devicesimulator.generator;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface ValueGenerator<V> {

	public V generate();
	
	public List<V> generate(int count);

	public ValueGenerator<V> step(int step);
	
	public ValueGenerator<List<V>> toListGenerator(int size);

	public ValueGenerator<Set<V>> toSetGenerator(int size);

	public ValueGenerator<String> toStringGenerator();

	public <U> ValueGenerator<U> map(Function<V, U> converter);

}
