package io.github.lmller.codetalks2017;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import io.github.lmller.codetalks2017.databinding.ActivityMainBinding;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	private Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://www.openthesaurus.de")
			.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
			.addConverterFactory(GsonConverterFactory.create())
			.build();

	private OpenThesaurus thesaurus = new OpenThesaurus(retrofit.create(OpenThesaurus.Api.class));
	private PublishSubject<String> searchQueryChangedSubject = PublishSubject.create();
	private SearchViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				searchQueryChangedSubject.onNext(text);
				return true;
			}
		});

		viewModel.observeTextChanges(searchQueryChangedSubject, Schedulers.io());
	}

}
