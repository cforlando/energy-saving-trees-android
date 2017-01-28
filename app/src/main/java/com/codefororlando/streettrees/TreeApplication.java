package com.codefororlando.streettrees;

import android.app.Application;
import android.content.Context;

import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;
import com.codefororlando.streettrees.api.providers.TreeProvider;
import com.codefororlando.streettrees.di.DaggerTreeProviderComponent;
import com.codefororlando.streettrees.di.TreeDescriptionProviderModule;
import com.codefororlando.streettrees.di.TreeProviderComponent;
import com.codefororlando.streettrees.di.TreeProviderModule;

/**
 * Created by johnli on 10/18/16.
 */
public class TreeApplication extends Application {

    private TreeProviderComponent treeProviderComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents(getApplicationContext());
    }

    private void initComponents(Context context) {
        TreeProvider treeProvider = new TreeProvider(getApplicationContext());
        TreeDescriptionProvider treeDescriptionProvider = new TreeDescriptionProvider(context);

        treeProviderComponent = DaggerTreeProviderComponent.builder()
                .treeProviderModule(new TreeProviderModule(treeProvider))
                .treeDescriptionProviderModule(new TreeDescriptionProviderModule(treeDescriptionProvider))
                .build();
    }

    public TreeProviderComponent getTreeProviderComponent() {
        return treeProviderComponent;
    }
}
