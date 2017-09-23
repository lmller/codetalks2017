package io.github.lmller.codetalks2017


import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

class OpenThesaurus(private val api: Api) : Thesaurus {

    override fun findSynonym(word: String): Observable<Synset> {
        return api.find(word)
                .flatMapObservable { (_, synsets) -> Observable.fromIterable(synsets) }
    }

    override fun name() = "openthesaurus.org"

    interface Api {
        @GET("synonyme/search?format=application/json")
        fun find(@Query("q") word: String): Single<SynonymList>
    }
}
