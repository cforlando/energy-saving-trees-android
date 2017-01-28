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
