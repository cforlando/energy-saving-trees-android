package com.codefororlando.streettrees.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefororlando.streettrees.R;

/**
 * Created by jdonlan on 6/4/16.
 */
public class DetailFragment extends Fragment {

    public static final String TAG = "DETAILFRAGMENT";

    public static DetailFragment newInstance() {
        DetailFragment frag = new DetailFragment();

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_activity, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
