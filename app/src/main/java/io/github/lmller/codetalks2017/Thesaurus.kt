package io.github.lmller.codetalks2017


import io.reactivex.Observable

interface Thesaurus {
    fun findSynonym(word: String): Observable<Synset>

    fun name(): String
}
