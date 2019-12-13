package ca.mss.syllogism;

public class Term {
	public final String text;

	Term(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return text.equals(obj.toString());
	}

	
}
