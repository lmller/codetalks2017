package io.github.lmller.codetalks2017;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import javax.inject.Inject;

import io.github.lmller.codetalks2017.databinding.ActivityMainBinding;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

	@Inject
	OpenThesaurus thesaurus;

	private PublishSubject<String> searchQueryChangedSubject = PublishSubject.create();
	private SearchViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.getInjector().injectInto(this);

		ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		viewModel = new SearchViewModel(thesaurus);

		binding.setViewModel(viewModel);

		binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				return true;
			}

			@Override
			public boolean onQueryTextChange(String text) {
				searchQueryChangedSubject.onNext(text.trim());
				return true;
			}
		});

		viewModel.observeTextChanges(searchQueryChangedSubject, Schedulers.io());
	}

}
