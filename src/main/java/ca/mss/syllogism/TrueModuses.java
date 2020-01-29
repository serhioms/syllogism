package ca.mss.syllogism;

import java.util.Arrays;

public class TrueModuses {

	public static final int FIGURE_1 = 0;
	public static final int FIGURE_2 = 1;
	public static final int FIGURE_3 = 2;
	public static final int FIGURE_4 = 3;
	
	private final String[][] trueModuses;
	
	public TrueModuses(String[][] trueModuses) {
		this.trueModuses = trueModuses;
	}

	public boolean contains1(String modus) {
		return Arrays.stream(trueModuses[FIGURE_1]).anyMatch(modus::equals);
	}

	public boolean contains2(String modus) {
		return Arrays.stream(trueModuses[FIGURE_2]).anyMatch(modus::equals);
	}

	public boolean contains3(String modus) {
		return Arrays.stream(trueModuses[FIGURE_3]).anyMatch(modus::equals);
	}

	public boolean contains4(String modus) {
		return Arrays.stream(trueModuses[FIGURE_4]).anyMatch(modus::equals);
	}
}
