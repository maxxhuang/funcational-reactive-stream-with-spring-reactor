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
