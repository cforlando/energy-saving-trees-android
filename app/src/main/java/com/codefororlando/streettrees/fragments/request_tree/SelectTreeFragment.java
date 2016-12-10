package com.codefororlando.streettrees.fragments.request_tree;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.view.BlurBuilder;
import com.codefororlando.streettrees.view.ImageViewPagerAdapter;
import com.codefororlando.streettrees.view.PageFragment;

/**
 * Created by johnli on 9/24/16.
 */
public class SelectTreeFragment extends PageFragment {

    private static final String PAGER_INDEX_KEY = "PAGER_INDEX_KEY";

    Button nextButton;
    ViewPager pager;
    ImageViewPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ImageViewPagerAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_select, container, false);
        int pageIndex = 0;
        if(savedInstanceState != null) {
            pageIndex = savedInstanceState.getInt(PAGER_INDEX_KEY, 0);
        }
        bindUi(view, pageIndex);
        initBlurredBackground(view);
        return  view;
    }

    void bindUi(View view, int pageIndex) {
        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });
        pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);
        adapter.setImages(getResources(), R.array.tree_images);
        pager.setCurrentItem(pageIndex);
    }

    void initBlurredBackground(View view) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bg_forrest);
        Bitmap blurredBackground = BlurBuilder.blur(getActivity(), largeIcon, .05f, 25);
        Drawable d = new BitmapDrawable(getResources(), blurredBackground);
        view.setBackground(d);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGER_INDEX_KEY, pager.getCurrentItem());
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.select_tree_fragment_title));
    }

    void nextFragment() {
        pageListener.next();
    }

}
