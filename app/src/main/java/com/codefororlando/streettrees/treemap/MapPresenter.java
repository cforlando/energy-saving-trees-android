// Copyright © 2017 Code for Orlando.
// 
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.codefororlando.streettrees.treemap;

import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.providers.TreeProvider;
import com.codefororlando.streettrees.util.TreeMapUtil;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapPresenter {

    private MapView view;
    private TreeProvider treeProvider;
    private Subscription treeSubscription;

    public void attach(TreeProvider provider, MapView view) {
        this.view = view;
        treeProvider = provider;
    }

    public void detach() {
        view = null;
        if (treeSubscription != null && !treeSubscription.isUnsubscribed()) {
            treeSubscription.unsubscribe();
        }
    }

    public void fetchTrees() {
        treeSubscription = treeProvider.getTreesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Tree>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Tree> trees) {
                        view.updateMapWithTrees(trees);
                    }
                });
    }

    public List<Tree> getVisibleTrees(VisibleRegion vr, List<Tree> trees, int limit) {
        return TreeMapUtil.getVisibleTrees(vr, trees, limit);
    }

    public interface MapView {
        void updateMapWithTrees(List<Tree> trees);
    }
}
