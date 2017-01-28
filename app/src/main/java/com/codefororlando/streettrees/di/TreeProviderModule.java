package com.codefororlando.streettrees.di;

import com.codefororlando.streettrees.api.providers.TreeProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by johnli on 10/18/16.
 */
@Module
public class TreeProviderModule {

    private final TreeProvider treeProvider;

    public TreeProviderModule(TreeProvider provider) {
        this.treeProvider = provider;
    }

    @Provides
    @Singleton
    public TreeProvider providesTreeProvider() {
        return treeProvider;
    }
}
