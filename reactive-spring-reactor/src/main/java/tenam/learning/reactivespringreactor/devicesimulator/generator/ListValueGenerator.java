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
