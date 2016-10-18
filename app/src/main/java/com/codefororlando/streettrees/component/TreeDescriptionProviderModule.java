package com.codefororlando.streettrees.component;

import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by johnli on 10/18/16.
 */
@Module
public class TreeDescriptionProviderModule {
    private TreeDescriptionProvider treeDescriptionProvider;

    public TreeDescriptionProviderModule(TreeDescriptionProvider provider) {
        this.treeDescriptionProvider = provider;
    }

    @Provides
    @Singleton
    public TreeDescriptionProvider providesTreeDescriptionProvider() {
        return treeDescriptionProvider;
    }
}
