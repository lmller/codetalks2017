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
	public Observable<Models.Synset> findSynonym(String word) {
		return api.find(word)
				.flatMapObservable(synonymList -> Observable.fromIterable(synonymList.synsets));
	}

	public interface Api {
		@GET("synonyme/search?format=application/json")
		Single<Models.SynonymList> find(@Query("q") String word);
	}
}
