package com.codefororlando.streettrees.requesttree.selecttree;

import com.codefororlando.streettrees.api.models.TreeDescription;
import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;
import com.codefororlando.streettrees.util.TreeDrawableResourceProvider;

import java.util.ArrayList;
import java.util.List;


public class SelectTreePresenter {

    private SelectTreeView view;

    private final TreeDescriptionProvider treeDescriptionProvider;
    private final TreeDrawableResourceProvider treeDrawableResourceProvider;

    private List<TreeViewModel> treeViewModels;

    public SelectTreePresenter(TreeDescriptionProvider treeDescriptionProvider,
                               TreeDrawableResourceProvider treeDrawableResourceProvider) {
        this.treeDescriptionProvider = treeDescriptionProvider;
        this.treeDrawableResourceProvider = treeDrawableResourceProvider;
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
        for (TreeDescription description : treeDescriptions) {
            TreeViewModel viewModel = new TreeViewModel(description, treeDrawableResourceProvider.getDrawable(description.getName()));
            treeViewModels.add(viewModel);
        }
        view.present(treeViewModels);
    }

    public TreeViewModel getTreeViewModel(int index) {
        if (index < treeViewModels.size()) {
            return treeViewModels.get(index);
        }
        return null;
    }

    public interface SelectTreeView {
        void present(List<TreeViewModel> treeViewModels);
    }
}
