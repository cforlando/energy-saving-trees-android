package com.codefororlando.streettrees.component;

import com.codefororlando.streettrees.activity.DetailActivity;
import com.codefororlando.streettrees.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by johnli on 10/18/16.
 */

@Singleton
@Component(modules = { TreeProviderModule.class, TreeDescriptionProviderModule.class })
public interface TreeProviderComponent {
    void inject(MainActivity target);
    void inject(DetailActivity target);
}
