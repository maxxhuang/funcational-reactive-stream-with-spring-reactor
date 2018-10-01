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
