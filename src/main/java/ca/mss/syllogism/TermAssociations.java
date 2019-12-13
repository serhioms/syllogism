package ca.mss.syllogism;

import java.util.Collection;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

public class TermAssociations {
	
	MultiMap<String, CategoricalSentence> associations = new MultiValueMap<>();

	@SuppressWarnings("unchecked")
	public Collection<CategoricalSentence> get(String key) {
		return (Collection<CategoricalSentence> )associations.get(key);
	}

	public void put(CategoricalSentence sentence) {
		associations.put(sentence.left.text, sentence);
		associations.put(sentence.right.text, sentence);
	}

	public boolean isEmpty() {
		return associations.isEmpty();
	}
}
