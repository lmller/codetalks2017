package io.github.lmller.codetalks2017.di;

import dagger.Module;
import dagger.Provides;
import io.github.lmller.codetalks2017.OpenThesaurus;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

	@Provides
	Retrofit provideRetrofit() {
		final OkHttpClient httpClient = new OkHttpClient.Builder()
				.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
				.build();
		return new Retrofit.Builder()
				.baseUrl("https://www.openthesaurus.de")
				.client(httpClient)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	@Provides
	OpenThesaurus provideThesaurus(Retrofit retrofit){
		return new OpenThesaurus(retrofit.create(OpenThesaurus.Api.class));
	}
}