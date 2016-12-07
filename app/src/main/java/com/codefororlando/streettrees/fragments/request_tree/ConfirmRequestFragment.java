package com.codefororlando.streettrees.fragments.request_tree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.view.PageFragment;

/**
 * Created by johnli on 9/24/16.
 */
public class ConfirmRequestFragment extends PageFragment {

    Button nextButton;

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.confirm_request_fragment_title));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_confirmation, container, false);
        bindView(view);
        return  view;
    }

    void bindView(View view) {
        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageListener.next();
            }
        });
    }
}
