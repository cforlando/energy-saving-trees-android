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

package com.codefororlando.streettrees.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.TreeDescription;

public class DetailFragment extends Fragment {

    public static final String TAG = "DETAILFRAGMENT";
    private DetailListener activityListener;
    private View layoutView;

    public static DetailFragment newInstance() {
        DetailFragment frag = new DetailFragment();

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailListener) {
            activityListener = (DetailListener) context;
        } else {
            throw new IllegalArgumentException("Cannot attach DetailFragment to Activity without DetailListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_detail_activity, container, false);
        return layoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TreeDescription treeDescription = null;
		try {
			treeDescription = activityListener.getTreeData();
		} catch(NullPointerException np){
			this.getActivity().finish();
			return;
		}

		if(treeDescription == null){
			this.getActivity().finish();
			return;
		}

        ((TextView) layoutView.findViewById(R.id.treeHeight)).setText(treeDescription.getMinHeight());
        ((TextView) layoutView.findViewById(R.id.treeLeaf)).setText(treeDescription.getLeaf());
        ((TextView) layoutView.findViewById(R.id.treeShape)).setText(treeDescription.getShape());
        ((TextView) layoutView.findViewById(R.id.treeWidth)).setText(treeDescription.getMinWidth());
        ((TextView) layoutView.findViewById(R.id.treeSunlight)).setText(treeDescription.getSunlight());
        ((TextView) layoutView.findViewById(R.id.treSoil)).setText(treeDescription.getSoil());
        ((TextView) layoutView.findViewById(R.id.treeMoisture)).setText(treeDescription.getMoisture());
        ((TextView) layoutView.findViewById(R.id.treeDescription)).setText(treeDescription.getDescription());
        ((TextView) layoutView.findViewById(R.id.treeType)).setText(treeDescription.getName());
    }

    public interface DetailListener {
        TreeDescription getTreeData();
    }

}
