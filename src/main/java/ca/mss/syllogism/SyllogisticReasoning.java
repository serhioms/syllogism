package ca.mss.syllogism;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SyllogisticReasoning {

	private static final Optional<Boolean> FALSE = Optional.of(false);
	private static final Optional<Boolean> TRUE = Optional.of(true);
	private static final Optional<Boolean> EMPTY = Optional.empty();

	private static final boolean BOOLEAN_FIGURE_3_4 = false;
	private static final boolean BOOLEAN_FIGURE_1_2 = true;

	private final List<String> figure1Moduses = Arrays.asList("aaa", "eae", "aii", "eio", "aai", "eao");
	private final List<String> figure2Moduses = Arrays.asList("eae", "aee", "eio", "aoo", "eao", "aeo");
	private final List<String> figure3Moduses = Arrays.asList("aii", "iai", "eio", "oao", "eao", "aai");
	private final List<String> figure4Moduses = Arrays.asList("aee", "iai", "eio", "aeo", "eao", "aai");

	private final TermAssociations associations = new TermAssociations();

	public void addSentence(Term left, Copula copula, Term right) {
		addSentence(new CategoricalSentence(left, copula, right));
	}

	public void addSentence(CategoricalSentence sentence) {
		associations.put(sentence);
	}

	public Optional<Boolean> interrogate(Term left, Copula copula, Term right) {
		return interrogate(new CategoricalSentence(left, copula, right));
	}	
	
	public Optional<Boolean> interrogate(CategoricalSentence conclusion) {

		Collection<CategoricalSentence> relations = associations.get(conclusion.left.text);
		
		if (relations == null || relations.isEmpty()) {
			return EMPTY;
		}

		Optional<CategoricalSentence> result = relations.stream()
				.filter((CategoricalSentence minor) -> minor.equals(conclusion) ? true : interrogate(conclusion, minor).get())
				.findFirst();

		return result.isPresent()? TRUE: FALSE;
	}

	public Optional<Boolean> interrogate(CategoricalSentence conclusion, CategoricalSentence minor) {

		boolean whichFigure;
		Collection<CategoricalSentence> memeRelations;

		if (conclusion.left.equals(minor.left)) {
			memeRelations = associations.get(minor.right.text);
			whichFigure = BOOLEAN_FIGURE_1_2;
		} else if (conclusion.left.equals(minor.right)) {
			memeRelations = associations.get(minor.left.text);
			whichFigure = BOOLEAN_FIGURE_3_4;
		} else {
			throw new RuntimeException("Unknown figure for: conclusion("+conclusion+") vs minor("+minor+")");
		}

		if (memeRelations == null || memeRelations.isEmpty()) {
			return FALSE;
		}

		Optional<CategoricalSentence> result = memeRelations.stream()
				.filter((CategoricalSentence major) -> !major.equals(minor))
				.filter((CategoricalSentence major) -> {
					String modus = conclusion.modus(major, minor);

					if (whichFigure) {
						if (major.right.equals(minor.right)) {
							if( figure2Moduses.contains(modus)) {
								if( major.left.equals(conclusion.right) ) {
									conclusion.addPositiveSyllogism(modus, major, minor);
									return true;
								}
							}
						} else {
							if( figure1Moduses.contains(modus) ) {
								if( major.right.equals(conclusion.right) ) {
									conclusion.addPositiveSyllogism(modus, major, minor);
									return true;
								}
							}
						}
					} else {
						if (major.left.equals(minor.left)) {
							if( figure3Moduses.contains(modus) ) {
								if( major.right.equals(conclusion.right) ) {
									conclusion.addPositiveSyllogism(modus, major, minor);
									return true;
								}
							}
						} else {
							if( figure4Moduses.contains(modus) ) {
								if( major.left.equals(conclusion.right) ) {
									conclusion.addPositiveSyllogism(modus, major, minor);
									return true;
								}
							}
						}
					}
					conclusion.addNegativeSyllogism(modus, major, minor);
					return false;
				}).findFirst();

		return result.isPresent()? TRUE: FALSE;
	}

	public Term createTerm(String text) {
		if( associations.isEmpty() ) {
			return new Term(text.intern());
		}
		Collection<CategoricalSentence> relations = associations.get(text);
		if( relations == null || relations.isEmpty() ) {
			return new Term(text.intern());
		}
		@SuppressWarnings("unlikely-arg-type")
		Optional<Term> result = relations.stream()
				.map((CategoricalSentence sentense) -> sentense.left.equals(text)? sentense.left: sentense.right)
				.filter((Term term) -> term.equals(text))
				.findFirst();
		return result.isPresent()? result.get(): new Term(text);
	}
}
