package ca.mss.syllogism;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

public class KnowledgeBase {
	
	private final static Collection<Proposition> EMPTY = Collections.unmodifiableCollection(Collections.emptyList());
	
	private MultiMap<String, Proposition> relations = new MultiValueMap<>();

	@SuppressWarnings("unchecked")
	private Collection<Proposition> get(String termText) {
		Collection<Proposition> result = (Collection<Proposition> )relations.get(termText);
		return result != null? result: EMPTY; 
	}

	public Collection<Proposition> get(Term term) {
		return get(term.text); 
	}

	public void put(Term term, Proposition proposition) {
		if( !get(term.text).contains(proposition) ){
			relations.put(term.text, proposition);
		}
	}

	public boolean isEmpty() {
		return relations.isEmpty();
	}

	@Override
	public String toString() {
		return String.valueOf(relations.size());
	}

	@SuppressWarnings("unlikely-arg-type")
	public Term findTerm(String termText) {
		Collection<Proposition> collection = get(termText);
		if( !collection.isEmpty() ) {
			Optional<Term> result = collection.stream()
					.map((Proposition proposition) -> proposition.subjectTerm.equals(termText)? proposition.subjectTerm: proposition.predicateTerm)
					.filter((Term t) -> t.equals(termText))
					.findFirst();
			if( result.isPresent() ) {
				return result.get();
			}
		}
		return null;
	}

	public Proposition findProposition(Term subjectTerm, Copula copula, Term predicateTerm) {
		Collection<Proposition> collection = get(predicateTerm);
		if( !collection.isEmpty() ) {
			Optional<Proposition> result = collection.stream()
					.filter((Proposition proposition) -> proposition.subjectTerm == subjectTerm)
					.findFirst();
			if( result.isPresent() ) {
				return result.get();
			}
		}
		collection = get(subjectTerm);
		if( !collection.isEmpty() ) {
			Optional<Proposition> result = collection.stream()
					.filter((Proposition proposition) -> proposition.predicateTerm == predicateTerm)
					.findFirst();
			if( result.isPresent() ) {
				return result.get();
			}
		}
		return null;
	}
	
	
}
