package tenam.learning.reactivespringreactor.devicesimulator.generator;

import java.util.List;
import java.util.Random;

public class ListValueGenerator<V> extends AbstractValueGenerator<List<V>> {

	private ValueGenerator<V> componentGenerator;
	
	private int size;
	
	private boolean isRandom = false;
	
	private Random random = new Random(System.currentTimeMillis());
	
	
	public ListValueGenerator(ValueGenerator<V> componentGenerator, int size) {
		this.componentGenerator = componentGenerator;
		this.size = size;
	}
	
	
	@Override
	public List<V> generate() {
		int listSize = determineListSize();
		return this.componentGenerator.generate(listSize);
	}


	public int getSize() {
		return size;
	}


	public ListValueGenerator<V> setSize(int size) {
		this.size = size;
		return this;
	}


	public boolean isRandom() {
		return isRandom;
	}


	public ListValueGenerator<V> setRandom(boolean isRandom) {
		this.isRandom = isRandom;
		return this;
	}

	private int determineListSize() {
		if (this.isRandom) {
			return this.random.nextInt(this.size + 1);
		} else {
			return this.size;
		}
	}
}
