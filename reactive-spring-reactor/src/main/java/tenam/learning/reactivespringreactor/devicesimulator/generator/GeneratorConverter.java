package tenam.learning.reactivespringreactor.devicesimulator.generator;

import com.google.common.base.Preconditions;

abstract public class GeneratorConverter<F, T> extends AbstractValueGenerator<T> {

	private ValueGenerator<F> generator;
	
	public GeneratorConverter(ValueGenerator<F> generator) {
		Preconditions.checkNotNull(generator, "generator cannot be null.");
		this.generator = generator;
	}
	
	@Override
	final public T generate() {
		T converted = convert(generator.generate());
		return converted;
	}

	abstract protected T convert(F value);
}
