package io.github.lmller.codetalks2017

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SearchView
import io.github.lmller.codetalks2017.databinding.ActivityMainBinding
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var thesaurus: OpenThesaurus

    private val searchQueryChangedSubject = PublishSubject.create<String>()
    private var searchViewModel: SearchViewModel? = null
    private var disposable = Disposables.disposed()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.injector.injectInto(this)

        searchViewModel = SearchViewModel(thesaurus).apply {
            disposable = observeTextChanges(searchQueryChangedSubject, Schedulers.io())
        }

        with(DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)) {
            viewModel = searchViewModel

            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String) = true

                override fun onQueryTextChange(text: String): Boolean {
                    searchQueryChangedSubject.onNext(text.trim())
                    return true
                }
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
