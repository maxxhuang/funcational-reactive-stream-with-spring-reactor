package tenam.learning.reactivespringreactor.devicesimulator.generator;

import com.google.common.base.Preconditions;

public class IntArrayGenerator extends AbstractValueGenerator<int[]> {

	private int size;
	
	private int[] intArray;
	
	private int minValue = Integer.MIN_VALUE;
	private int maxValue = Integer.MAX_VALUE;
	
	
	public IntArrayGenerator(int size, int minValue, int maxValue, int... intValues) {
		Preconditions.checkArgument(size > 0, "size must be greater than 0.");
		Preconditions.checkArgument(maxValue >= minValue, "maxValue must be greater than or equal to minValue");
		
		this.size = size;
		this.minValue = minValue;
		this.maxValue = maxValue;
		
		this.intArray = createIntArray(size, minValue, maxValue, intValues);
	}
	
	private static int[] createIntArray(int size, int minValue, int maxValue, int[] intValues) {
		int[] array = new int[size];
		
		int index= 0;
		for (; index < intValues.length; ++index) {
			int value = intValues[index];
			Preconditions.checkState( value >= minValue && value <= maxValue,
					String.format("value must be between %d and %d: %d", minValue, maxValue, value));
			array[index] = value;
		}
		
		for (; index < size; ++index) {
			array[index] = minValue;
		}
		
		return array;
	}
	
	@Override
	public int[] generate() {
		int[] array = this.intArray.clone();
		increment();
		return array;
	}

	private void increment() {
		boolean carry = true;
		for (int i = this.size - 1; i >= 0 && carry == true; --i) {
			
			int value = this.intArray[i];
			
			if (value + 1 > this.maxValue) {
				carry = true;
				value = this.minValue;
			} else {
				value = value + 1;
				carry = false;
			}
			
			this.intArray[i] = value;
		}
		
	}
	
	public int getSize() {
		return size;
	}

	public IntArrayGenerator setSize(int size) {
		this.size = size;
		return this;
	}

	public int getMinValue() {
		return minValue;
	}

	public IntArrayGenerator setMinValue(int minValue) {
		this.minValue = minValue;
		return this;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public IntArrayGenerator setMaxValue(int maxValue) {
		this.maxValue = maxValue;
		return this;
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	
	
	abstract public static class FormattedIntArrayGenerator extends GeneratorConverter<int[], String> {

		protected IntArrayGenerator intArrayGenerator;
		
		protected String separator;
		protected boolean uppercase;
		
		protected String prefix = "";
		protected String suffix = "";
		
		
		public FormattedIntArrayGenerator(IntArrayGenerator generator, String separator, boolean uppercase) {
			super(generator);
			this.intArrayGenerator = generator;
			this.separator = separator;
			this.uppercase = uppercase;
		}

		@Override
		final protected String convert(int[] intArray) {
			String formattedString = formatIntArray(intArray);
			return formattedString;
		}

		protected String formatIntArray(int[] intArray) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(this.prefix);
			
			for (int i = 0; i < intArray.length; ++i) {
				if (i > 0) {
					sb.append(this.separator);
				}
				
				sb.append(formatIntValue(intArray[i], this.uppercase));
			}
			
			sb.append(this.suffix);
			
			return sb.toString();
		}
		
		abstract protected String formatIntValue(int intValue, boolean uppercase);
		
		public String getSeparator() {
			return separator;
		}

		public void setSeparator(String separator) {
			this.separator = separator;
		}

		public boolean isUppercase() {
			return uppercase;
		}

		public void setUppercase(boolean uppercase) {
			this.uppercase = uppercase;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public int getMinValue() {
			return intArrayGenerator.getMinValue();
		}

		protected FormattedIntArrayGenerator setMinValue(int minValue) {
			this.intArrayGenerator.setMinValue(minValue);
			return this;
		}

		public int getMaxValue() {
			return intArrayGenerator.getMaxValue();
		}

		protected FormattedIntArrayGenerator setMaxValue(int maxValue) {
			this.intArrayGenerator.setMaxValue(maxValue);
			return this;
		}
		
	}
}
