package com.codefororlando.streettrees.di;

import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TreeDescriptionProviderModule {
    private final TreeDescriptionProvider treeDescriptionProvider;

    public TreeDescriptionProviderModule(TreeDescriptionProvider provider) {
        this.treeDescriptionProvider = provider;
    }

    @Provides
    @Singleton
    public TreeDescriptionProvider providesTreeDescriptionProvider() {
        return treeDescriptionProvider;
    }
}
