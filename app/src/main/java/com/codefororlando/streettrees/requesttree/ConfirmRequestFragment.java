package com.codefororlando.streettrees.requesttree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codefororlando.streettrees.R;

public class ConfirmRequestFragment extends BlurredBackgroundFragment {

    private Button nextButton;

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
        float blurRadius = 25f;
        float blurScale = .05f;
        initBlurredBackground(view, R.drawable.bg_lake_trees, blurRadius, blurScale);
        return view;
    }

    private void bindView(View view) {
        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageListener.next();
            }
        });
    }
}
