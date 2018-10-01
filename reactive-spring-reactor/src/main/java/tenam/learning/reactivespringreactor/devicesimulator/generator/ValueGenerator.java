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
