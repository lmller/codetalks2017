package io.github.lmller.codetalks2017;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OpenThesaurus implements Thesaurus {

	private final Api api;

	public OpenThesaurus(Api api) {
		this.api = api;
	}

	@Override
	public Observable<Synset> findSynonym(String word) {
		return api.find(word)
				.flatMapObservable(synonymList -> Observable.fromIterable(synonymList.getSynsets()));
	}

	@Override
	public String name() {
		return "openthesaurus.org";
	}

	public interface Api {
		@GET("synonyme/search?format=application/json")
		Single<SynonymList> find(@Query("q") String word);
	}
}
