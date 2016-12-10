package com.codefororlando.streettrees.requesttree.selecttree;

import android.support.annotation.DrawableRes;

import com.codefororlando.streettrees.api.models.TreeDescription;

/**
 * Created by johnli on 12/10/16.
 */

public class TreeViewModel {

    private final TreeDescription tree;
    private final @DrawableRes int drawableResId;

    public TreeViewModel(TreeDescription tree, int drawableResId) {
        this.tree = tree;
        this.drawableResId = drawableResId;
    }

    public TreeDescription getTree() {
        return tree;
    }

    public int getDrawableResId() {
        return drawableResId;
    }
}
