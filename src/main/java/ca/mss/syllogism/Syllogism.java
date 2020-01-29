package ca.mss.syllogism;

public class Syllogism {

	final public Proposition major;
	final public Proposition minor;
	final public Proposition conclusion;
	final public String text;

	public Syllogism(Proposition major, Proposition minor, Proposition conclusion) {
		super();
		this.major = major;
		this.minor = minor;
		this.conclusion = conclusion;
		this.text = conclusion.modus(major, minor) + ":\n" + major + "\n" + minor + "\n" + conclusion;
	}

	public Syllogism(Proposition conclusion) {
		super();
		this.major = null;
		this.minor = null;
		this.conclusion = conclusion;
		this.text = conclusion.toString();
	}

	@Override
	public String toString() {
		return text;
	}
}
