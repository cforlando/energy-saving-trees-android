package com.codefororlando.streettrees.fragments.request_tree;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.view.ImageViewPagerAdapter;
import com.codefororlando.streettrees.view.PageFragment;

/**
 * Created by johnli on 9/24/16.
 */
public class SelectTreeFragment extends PageFragment {

    Button nextButton;
    ViewPager pager;
    ImageViewPagerAdapter adapter;

    int[] imageResIds = {R.drawable.cfo_elm, R.drawable.cfo_chinese_pistache,
            R.drawable.cfo_dahoon_holly, R.drawable.cfo_eagleston_holly,
            R.drawable.cfo_japanese_blueberry, R.drawable.cfo_live_oak,
            R.drawable.cfo_magnolia, R.drawable.cfo_myrtle, R.drawable.cfo_nuttal_oak,
            R.drawable.cfo_pink_trumpet, R.drawable.cfo_tulip_popular, R.drawable.cfo_yaupon_holly,
            R.drawable.cfo_eagleston_holly, R.drawable.cfo_yellow_trumpet};
    private TypedArray img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ImageViewPagerAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_select, container, false);
        bindUi(view);
        return  view;
    }

    void bindUi(View view) {
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

        img = getResources().obtainTypedArray(R.array.tree_images);
    }

    void nextFragment() {
        pageListener.next();
    }

}
