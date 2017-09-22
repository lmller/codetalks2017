package io.github.lmller.codetalks2017;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;


public class SearchViewModel extends BaseObservable {
	private final Thesaurus synonyms;

	private boolean isLoading;
	private List<Models.Term> searchResults = Collections.emptyList();
	private String errorMessage;

	public SearchViewModel(Thesaurus synonymSource) {
		this.synonyms = synonymSource;
	}

	public Disposable observeTextChanges(Observable<String> queryTextChange, Scheduler scheduler) {
		return queryTextChange
				.filter(text -> !text.isEmpty())
				.debounce(250, TimeUnit.MILLISECONDS, scheduler)
				.flatMap(text -> synonyms.findSynonym(text)
						.doOnSubscribe(s -> resetState())
						.doOnTerminate(() -> isLoading = false)
				).subscribe(
						result -> handleSearchResult(result),
						e -> {
							throw new OnErrorNotImplementedException(e);
						});
	}

	private void handleSearchResult(Models.Synset result) {
		searchResults = result.terms;

		notifyChange();
	}

	private void resetState() {
		searchResults = new ArrayList<>();
		errorMessage = null;
		notifyPropertyChanged(io.github.lmller.codetalks2017.BR.errorVisible);
	}

	@Bindable
	public String getSearchResults() {
		final List<String> terms = ListUtil.map(searchResults, t -> t.term);
		return ListUtil.join(terms, '\n');
	}

	@Bindable
	public boolean isProgressBarVisible() {
		return isLoading;
	}

	@Bindable
	public boolean isErrorVisible() {
		return errorMessage != null;
	}

	@Bindable
	public String getErrorMessage() {
		return errorMessage;
	}

}
