package io.github.lmller.codetalks2017

import android.databinding.BaseObservable
import android.databinding.Bindable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class SearchViewModel(private val synonyms: Thesaurus) : BaseObservable() {

    private val logTag: String = synonyms.name()

    private var searchResultsTerms = emptyList<Term>()
        set(value) {
            field = value

            notifyChange()
        }

    private var isLoading = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisible)
        }

    var errorMessage: String? = null
        @Bindable get
        private set(value) {
            field = value

            notifyPropertyChanged(BR.errorVisible)
        }

    val isErrorVisible: Boolean
        @Bindable
        get() = errorMessage != null

    val isProgressBarVisible: Boolean
        @Bindable get() = isLoading

    val searchResults: String
        @Bindable
        get() = searchResultsTerms.joinToString("\n") { term: Term -> term.term }

    fun observeTextChanges(queryTextChange: Observable<String>, scheduler: Scheduler = Schedulers.io()): Disposable {
        return queryTextChange
                .filter { text -> !text.isEmpty() }
                .debounce(250, TimeUnit.MILLISECONDS, scheduler)
                .flatMap { text ->
                    synonyms.findSynonym(text)
                            .doOnError { e ->
                                errorMessage = "$logTag responded with: ${e.message}"
                            }
                            .doOnSubscribe { _ -> resetState() }
                            .doOnTerminate { isLoading = false }
                            .onErrorReturnItem(Synset())
                }
                .subscribe { result ->
                    searchResultsTerms = result.terms
                }
    }

    private fun resetState() {
        searchResultsTerms = ArrayList()
        errorMessage = null
    }

}
