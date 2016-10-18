package com.codefororlando.streettrees;

import android.app.Application;
import android.content.Context;

import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;
import com.codefororlando.streettrees.api.providers.TreeProvider;
import com.codefororlando.streettrees.component.DaggerTreeProviderComponent;
import com.codefororlando.streettrees.component.TreeDescriptionProviderModule;
import com.codefororlando.streettrees.component.TreeProviderComponent;
import com.codefororlando.streettrees.component.TreeProviderModule;

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
