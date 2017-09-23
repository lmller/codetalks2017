package io.github.lmller.codetalks2017;


import io.reactivex.Observable;

public interface Thesaurus {
	Observable<Synset> findSynonym(String word);

	String name();
}
