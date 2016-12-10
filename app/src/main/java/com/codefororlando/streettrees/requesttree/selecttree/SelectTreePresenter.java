package com.codefororlando.streettrees.requesttree.selecttree;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.codefororlando.streettrees.TreeApplication;
import com.codefororlando.streettrees.api.models.TreeDescription;
import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;
import com.codefororlando.streettrees.util.BlurBuilder;
import com.codefororlando.streettrees.util.TreeDrawableResourceProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by johnli on 12/10/16.
 */

public class SelectTreePresenter {

    SelectTreeView view;

    @Inject
    TreeDescriptionProvider treeDescriptionProvider;

    @Inject
    TreeDrawableResourceProvider treeDrawableResourceProvider;
    private List<TreeViewModel> treeViewModels;

    public SelectTreePresenter(Context context) {
        ((TreeApplication)context.getApplicationContext()).getTreeProviderComponent().inject(this);
    }

    public void attach(SelectTreeView view) {
        this.view = view;
    }

    public void detach() {
        view = null;
    }

    public void loadTreeDescriptions() {
        List<TreeDescription> treeDescriptions = treeDescriptionProvider.getAllTreeDescriptions();
        treeViewModels = new ArrayList<>(treeDescriptions.size());
        for(TreeDescription description : treeDescriptions) {
            TreeViewModel viewModel = new TreeViewModel(description, treeDrawableResourceProvider.getDrawable(description.getName()));
            treeViewModels.add(viewModel);
        }
        view.present(treeViewModels);
    }

    public TreeViewModel getTreeViewModel(int index) {
        if(index < treeViewModels.size()) {
            return treeViewModels.get(index);
        }
        return null;
    }
    public Drawable getBlurredBackground(Context context, @DrawableRes int backgroundResId) {
        Resources resources = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(resources, backgroundResId);
        Bitmap blurredBackground = BlurBuilder.blur(context, largeIcon, .05f, 25);
        Drawable blurredDrawable = new BitmapDrawable(resources, blurredBackground);
        return blurredDrawable;
    }

    public interface SelectTreeView {
        Context getContext();
        void present(List<TreeViewModel> treeViewModels);
    }
}
