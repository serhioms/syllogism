package ca.mss.syllogism;

/**
*
* @author Sergey Moskovskiy
*/
public class Proposition {

	public final Copula copula;
	public final Term subjectTerm, predicateTerm;
	private final String txt;

	Proposition(Term subjectTerm, Copula copula, Term predicateTerm) {
		this.copula = copula;
		this.predicateTerm = predicateTerm;
		this.subjectTerm = subjectTerm;
		this.txt = copula.quantity+" "+subjectTerm+" "+copula.quality+" "+predicateTerm;
		if (subjectTerm.equals(predicateTerm)) {
			throw new RuntimeException("Illigal proposition where subject term and predicate term are same: "+txt);
		}
	}

	@Override
	public String toString() {
		return txt;
	}

	@Override
	public int hashCode() {
		return txt.hashCode();
	}

	public String modus(Proposition major, Proposition minor) {
		return major.copula.modus + minor.copula.modus + copula.modus;
	}
}
