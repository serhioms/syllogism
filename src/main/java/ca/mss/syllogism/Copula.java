package ca.mss.syllogism;

public enum Copula {
	IS(true, "ALL", "IS", "a"), 
	ISNOT(false, "ALL", "ISNOT", "e"), 
	SomeIS(true, "SOME", "IS", "i"), 
	SomeISNOT(false,"SOME", "ISNOT", "o"),
	;

	public final Boolean truthEquivalency;
	public final String quantity;
	public final String quality;
	public final String modus;

	Copula(Boolean truthEquivalency, String quantity, String quality, String modus) {
		this.truthEquivalency = truthEquivalency;
		this.quantity = quantity;
		this.quality = quality;
		this.modus = modus;
	}
}
