package tenam.learning.reactivespringreactor.devicesimulator.generator;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import tenam.learning.reactivespringreactor.devicesimulator.generator.IntArrayGenerator.FormattedIntArrayGenerator;

public class MacGenerator extends FormattedIntArrayGenerator {

	private static final int DEFAULT_MIN_VALUE = 0x00;
	private static final int DEFAULT_MAX_VALUE = 0xFF;
	
	private static final int GROUP_SIZE = 6;
	
	private static final String DEFAULT_SEPARATOR = ":";

	
	public MacGenerator(String... macSegments) {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE, macSegments);
	}
	
	public MacGenerator(int minValue, int maxValue, String... macSegments) {
		this(minValue, maxValue, createIntArray(macSegments));
	}
	
	public MacGenerator(int minValue, int maxValue, int... macSegments) {
		super(new IntArrayGenerator(GROUP_SIZE, minValue, maxValue, macSegments),
				DEFAULT_SEPARATOR, false);
	}
	
	
	private static int[] createIntArray(String... hex) {

		Preconditions.checkArgument(hex.length <= GROUP_SIZE,
				String.format("At most %d hex numbers", GROUP_SIZE));
		
		int[] intArray = new int[hex.length];
		
		for (int i = 0; i < hex.length; ++i) {
			int intValue = Integer.parseInt(hex[i], 16);
			intArray[i] = intValue;
		}

		return intArray;
	}
	
	@Override
	protected String formatIntValue(int intValue, boolean uppercase) {
		String hexString = Strings.padStart(Integer.toHexString(intValue), 2, '0');
		return uppercase ? hexString.toUpperCase() : hexString.toLowerCase();
	}

}
