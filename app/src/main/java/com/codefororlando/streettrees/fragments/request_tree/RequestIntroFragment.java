package com.codefororlando.streettrees.fragments.request_tree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.view.BlurBuilder;
import com.codefororlando.streettrees.view.PageFragment;

/**
 * Created by johnli on 9/24/16.
 */
public class RequestIntroFragment extends PageFragment {

    Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_intro, container, false);
        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageListener.next();
            }
        });

        initBlurredBackground(view);
        return  view;
    }

    void initBlurredBackground(View view) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bg_house_center_trees);
        Bitmap blurredBackground = BlurBuilder.blur(getActivity(), largeIcon, .05f, 25);
        Drawable d = new BitmapDrawable(getResources(), blurredBackground);
        view.setBackground(d);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.request_intro_fragment_title));
    }
}
