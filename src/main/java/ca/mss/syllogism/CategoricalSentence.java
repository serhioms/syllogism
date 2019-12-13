package ca.mss.syllogism;

import java.util.ArrayList;
import java.util.List;

/**
*
* @author Sergey Moskovskiy
*/
public class CategoricalSentence {

	public final Copula copula;
	public final Term left, right;
	private final String txt;

	public final List<String> positiveSyllogism = new ArrayList<>();
	public final List<String> negativeSyllogism = new ArrayList<>();
	
	public CategoricalSentence(Term left, Copula copula, Term right) {
		super();
		this.copula = copula;
		this.right = right;
		this.left = left;
		this.txt = copula.quantity+" "+left+" "+copula.quality+" "+right;
		if (left.equals(right)) {
			throw new RuntimeException("Illigal syllogism where left and right terms are equal: "+txt);
		}
	}

	@Override
	public String toString() {
		return txt;
	}

	@Override
	public boolean equals(Object obj) {
		return txt.equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return txt.hashCode();
	}

	public void addPositiveSyllogism(String modus, CategoricalSentence major, CategoricalSentence minor) {
		positiveSyllogism.add(modus + ":\n" + major + "\n" + minor + "\n" + this);
	}

	public void addNegativeSyllogism(String modus, CategoricalSentence major, CategoricalSentence minor) {
		negativeSyllogism.add(modus + ":\n" + major + "\n" + minor + "\n" + this);
	}

	public String modus(CategoricalSentence major, CategoricalSentence minor) {
		return major.copula.modus + minor.copula.modus + copula.modus;
	}
}
