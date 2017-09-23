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


public class SearchViewModel extends BaseObservable {
	private final Thesaurus synonyms;

	private boolean isLoading;
	private List<Term> searchResults = Collections.emptyList();
	private String errorMessage;
	private String logTag;

	public SearchViewModel(Thesaurus synonymSource) {
		this.synonyms = synonymSource;
		this.logTag = synonymSource.name();
	}

	public Disposable observeTextChanges(Observable<String> queryTextChange, Scheduler scheduler) {
		return queryTextChange
				.filter(text -> !text.isEmpty())
				.debounce(250, TimeUnit.MILLISECONDS, scheduler)
				.flatMap(text -> synonyms.findSynonym(text)
						.doOnError(e -> setErrorMessage(e))
						.doOnSubscribe(s -> resetState())
						.doOnTerminate(() -> isLoading = false)
						.onErrorReturnItem(new Synset())
				)
				.subscribe(
						result -> handleSearchResult(result)
				);
	}

	private void setErrorMessage(Throwable e) {
		errorMessage = logTag + " responded with: " + e.getMessage();
		notifyPropertyChanged(BR.errorVisible);
	}

	private void handleSearchResult(Synset result) {
		searchResults = result.getTerms();

		notifyChange();
	}

	private void resetState() {
		searchResults = new ArrayList<>();
		errorMessage = null;
		notifyPropertyChanged(BR.errorVisible);
		notifyPropertyChanged(BR.searchResults);
	}

	@Bindable
	public String getSearchResults() {
		final List<String> terms = ListUtil.map(searchResults, t -> t.getTerm());
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
