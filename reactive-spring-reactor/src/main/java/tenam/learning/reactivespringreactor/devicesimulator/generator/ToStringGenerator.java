package tenam.learning.reactivespringreactor.devicesimulator.generator;

public class ToStringGenerator<F> extends GeneratorConverter<F, String> {

	public ToStringGenerator(ValueGenerator<F> generator) {
		super(generator);
	}

	@Override
	protected String convert(F value) {
		return value == null ? null : value.toString();
	}

	@Override
	public ValueGenerator<String> toStringGenerator() {
		return this;
	}

}
