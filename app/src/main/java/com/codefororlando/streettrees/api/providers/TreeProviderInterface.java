package com.codefororlando.streettrees.api.providers;

import com.codefororlando.streettrees.api.models.Tree;

import java.util.List;

/**
 * Created by johnli on 10/18/16.
 */
public interface TreeProviderInterface {

    public interface TreeProviderResultHandler<T> {
        void onComplete(boolean isSuccess, List<T> result);
    }

    void getTrees(final TreeProviderResultHandler<Tree> handler);
}
