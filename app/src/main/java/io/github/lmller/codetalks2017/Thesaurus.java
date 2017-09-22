package io.github.lmller.codetalks2017;


import io.reactivex.Observable;

public interface Thesaurus {
	Observable<Models.Synset> findSynonym(String word);
}
