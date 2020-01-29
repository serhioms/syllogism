package ca.mss.syllogism;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SyllogisticReasoning {

	private final TrueModuses trueModuses = new TrueModuses(new String[][] {
			{"aaa", "eae", "aii", "eio", "aai", "eao"},
			{"eae", "aee", "eio", "aoo", "eao", "aeo"},
			{"aii", "iai", "eio", "oao", "eao", "aai"},
			{"aee", "iai", "eio", "aeo", "eao", "aai"}});

	private final KnowledgeBase extensionKb = new KnowledgeBase();
	private final KnowledgeBase intensionKb = new KnowledgeBase();

	public void addProposition(Term left, Copula copula, Term right) {
		addProposition(createProposition(left, copula, right));
	}

	public void addProposition(Proposition proposition) {
		extensionKb.put(proposition.predicateTerm, proposition);  
		intensionKb.put(proposition.subjectTerm, proposition); 
	}

	public Optional<List<Syllogism>> validate(Term subjTerm, Copula copula, Term predTerm) {
		return validate(createProposition(subjTerm, copula, predTerm));
	}	
	
	public Optional<List<Syllogism>> validate(Proposition conclusion) {

		Collection<Proposition> minorPremises = intensionKb.get(conclusion.subjectTerm);
		if( minorPremises.contains(conclusion) ) {
			Optional<List<Syllogism>> trivial = Optional.of(new ArrayList<>());
			trivial.get().add(new Syllogism(conclusion));
			return trivial;
		}
		Optional<List<Syllogism>> result = minorPremises.stream()
				.map((Proposition minor) -> validateFigure12(conclusion, minor) )
				.filter((List<Syllogism> r) -> r != null )
				.findFirst();

		if( result.isPresent() && !result.get().isEmpty() ) {
			return result;
		}
		
		minorPremises = extensionKb.get(conclusion.subjectTerm);
		if( minorPremises.contains(conclusion) ) {
			Optional<List<Syllogism>> trivial = Optional.of(new ArrayList<>());
			trivial.get().add(new Syllogism(conclusion));
			return trivial;
		}
		result = minorPremises.stream()
					.map((Proposition minor) -> validateFigure34(conclusion, minor) )
					.filter((List<Syllogism> r) -> r != null )
					.findFirst();
		
		return result;
	}
	
	private List<Syllogism> validateFigure12(Proposition conclusion, Proposition minor) {
		Collection<Proposition> majorPremises = intensionKb.get(minor.predicateTerm);
		if( !majorPremises.isEmpty() ) {
			List<Syllogism> result = majorPremises.stream()
				.filter((Proposition major) -> major.predicateTerm.equals(conclusion.predicateTerm) )
				.filter((Proposition major) -> trueModuses.contains1(conclusion.modus(major, minor)) )
				.map((Proposition major) -> new Syllogism(major, minor, conclusion) )
				.collect(Collectors.toList());
			if( !result.isEmpty() ) {
				return result;
			}
		}
		
		majorPremises = extensionKb.get(minor.predicateTerm);
		if( !majorPremises.isEmpty() ) {
			List<Syllogism> result = majorPremises.stream()
				.filter((Proposition major) -> major.subjectTerm.equals(conclusion.predicateTerm) )
				.filter((Proposition major) -> trueModuses.contains2(conclusion.modus(major, minor)) )
				.map((Proposition major) -> new Syllogism(major, minor, conclusion) )
				.collect(Collectors.toList());
			if( !result.isEmpty() ) {
				return result;
			}
		}

		return null;
	}

	private List<Syllogism> validateFigure34(Proposition conclusion, Proposition minor) {
		List<Syllogism> result = new ArrayList<>();

		Collection<Proposition> majorPremises = intensionKb.get(minor.subjectTerm);
		if( !majorPremises.isEmpty() ) {
			if( majorPremises.stream()
				.filter((Proposition major) -> major.predicateTerm.equals(conclusion.predicateTerm) )
				.filter((Proposition major) -> trueModuses.contains3(conclusion.modus(major, minor)) )
				.filter((Proposition major) -> result.add(new Syllogism(major, minor, conclusion)) )
				.findFirst()
				.isPresent()) {
				return result;
			}
		}

		majorPremises = extensionKb.get(minor.subjectTerm);
		if( !majorPremises.isEmpty() ) {
			if( majorPremises.stream()
				.filter((Proposition major) -> major.subjectTerm.equals(conclusion.predicateTerm) )
				.filter((Proposition major) -> trueModuses.contains4(conclusion.modus(major, minor)) )
				.filter((Proposition major) -> result.add(new Syllogism(major, minor, conclusion)) )
				.findFirst()
				.isPresent()) {
				return result;
			}
		}
		
		return result;
	}

	public Term createTerm(String termText) {
		if( extensionKb.isEmpty() && intensionKb.isEmpty() ) {
			return new Term(termText.intern());
		}
		Term term = extensionKb.findTerm(termText);
		if( term != null ) {
			return term;
		}
		term = intensionKb.findTerm(termText);
		if( term != null ) {
			return term;
		}
		return new Term(termText.intern());
	}
	
	public Proposition createProposition(Term subjectTerm, Copula copula, Term predicateTerm) {
		if( extensionKb.isEmpty() && intensionKb.isEmpty() ) {
			return new Proposition(subjectTerm, copula, predicateTerm);
		}
		Proposition proposition = extensionKb.findProposition(subjectTerm, copula, predicateTerm);
		if( proposition != null ) {
			return proposition;
		}
		proposition = intensionKb.findProposition(subjectTerm, copula, predicateTerm);
		if( proposition != null ) {
			return proposition;
		}
		return new Proposition(subjectTerm, copula, predicateTerm);
	}

}
