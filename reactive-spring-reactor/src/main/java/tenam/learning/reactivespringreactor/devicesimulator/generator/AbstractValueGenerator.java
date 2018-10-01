/**
 * Copyright 2013 Ruckus Wireless, Inc. All rights reserved.
 *
 * RUCKUS WIRELESS, INC. CONFIDENTIAL - 
 * This is an unpublished, proprietary work of Ruckus Wireless, Inc., and is 
 * fully protected under copyright and trade secret laws. You may not view, 
 * use, disclose, copy, or distribute this file or any information contained 
 * herein except pursuant to a valid license from Ruckus. 
 */
 
package tenam.learning.reactivespringreactor.devicesimulator.generator;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract public class AbstractValueGenerator<T> implements ValueGenerator<T> {

	@Override
	public List<T> generate(int count) {
		Preconditions.checkArgument(count >= 0 , "count must be greater than or equal to 0: " + count);
		
		List<T> result = new ArrayList<T>(count);
		
		for (int i = 0; i < count; ++i) {
			result.add(this.generate());
		}
		
		return result;
	}

	@Override
	public ValueGenerator<T> step(int step) {
		return new StepGenerator<T>(step, this);
	}

	@Override
	public ListValueGenerator<T> toListGenerator(int size) {
		return new ListValueGenerator<T>(this, size);
	}

	@Override
	public SetValueGenerator<T> toSetGenerator(int size) {
		return new SetValueGenerator<T>(this, size);
	}

	@Override
	public ValueGenerator<String> toStringGenerator() {
		return new ToStringGenerator<T>(this);
	}

	@Override
	public <U> ValueGenerator<U> map(final Function<T, U> converter) {
		final ValueGenerator<T> original = this;
		return new GeneratorConverter<T, U>(original) {

			@Override
			protected U convert(T value) {
				return converter.apply(value);
			}

			@Override
			public ValueGenerator<String> toStringGenerator() {
				return original.toStringGenerator();
			}
		};
	}
}
