package com.codefororlando.streettrees.api.providers;

import com.codefororlando.streettrees.api.models.Tree;

import java.util.List;

public interface TreeProviderInterface {

    interface TreeProviderResultHandler<T> {
        void onComplete(boolean isSuccess, List<T> result);
    }

    void getTrees(final TreeProviderResultHandler<Tree> handler);
}
