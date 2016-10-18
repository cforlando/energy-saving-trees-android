package com.codefororlando.streettrees.component;

import com.codefororlando.streettrees.api.providers.TreeProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by johnli on 10/18/16.
 */
@Module
public class TreeProviderModule {

    private TreeProvider treeProvider;

    public TreeProviderModule(TreeProvider provider) {
        this.treeProvider = provider;
    }

    @Provides @Singleton
    public TreeProvider providesTreeProvider() {
        return treeProvider;
    }
}
