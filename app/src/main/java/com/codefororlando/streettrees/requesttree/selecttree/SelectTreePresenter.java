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
