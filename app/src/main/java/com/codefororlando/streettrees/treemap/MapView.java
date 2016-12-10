package com.codefororlando.streettrees.treemap;

import com.codefororlando.streettrees.api.models.Tree;

import java.util.List;

/**
 * Created by johnli on 10/16/16.
 */
public interface MapView {

    void updateMapWithTrees(List<Tree> trees);
}
