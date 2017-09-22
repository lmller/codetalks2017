package io.github.lmller.codetalks2017.di;

import dagger.Component;
import io.github.lmller.codetalks2017.MainActivity;


@Component(modules = {NetworkModule.class})
public interface DiComponent {

	void injectInto(MainActivity mainActivity);
}