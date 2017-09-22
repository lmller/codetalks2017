package io.github.lmller.codetalks2017;

import android.app.Application;

import io.github.lmller.codetalks2017.di.DaggerDiComponent;
import io.github.lmller.codetalks2017.di.DiComponent;
import io.github.lmller.codetalks2017.di.NetworkModule;


public class App extends Application {
	private static DiComponent injector;

	@Override
	public void onCreate() {
		super.onCreate();

		injector = DaggerDiComponent.builder()
				.networkModule(new NetworkModule())
				.build();
	}

	public static DiComponent getInjector() {
		return injector;
	}
}
