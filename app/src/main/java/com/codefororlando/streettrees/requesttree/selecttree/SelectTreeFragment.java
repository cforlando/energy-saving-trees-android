package com.codefororlando.streettrees.requesttree.selecttree;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.TreeDescription;
import com.codefororlando.streettrees.view.ImageViewPagerAdapter;
import com.codefororlando.streettrees.view.PageFragment;

import java.util.List;

/**
 * Created by johnli on 9/24/16.
 */
public class SelectTreeFragment extends PageFragment implements SelectTreePresenter.SelectTreeView,
        ViewPager.OnPageChangeListener{

    private static final String PAGER_INDEX_KEY = "PAGER_INDEX_KEY";

    Button nextButton;
    ViewPager pager;
    ImageViewPagerAdapter adapter;

    TextView descriptionLabel;
    TextView widthLabel;
    TextView heightLabel;
    TextView leafLabel;
    TextView shapeLabel;

    SelectTreePresenter presenter;
    private int pageIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ImageViewPagerAdapter(getActivity());
        presenter = new SelectTreePresenter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_select, container, false);
        if(savedInstanceState != null) {
            pageIndex = savedInstanceState.getInt(PAGER_INDEX_KEY, 0);
        } else {
            pageIndex = 0;
        }
        bindUi(view);
        initBlurredBackground(view);
        return  view;
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
        presenter.attach(this);
        presenter.loadTreeDescriptions();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public void present(List<TreeViewModel> treeViewModels) {
        int count = treeViewModels.size();
        int[] imageResIds = new int[count];
        for(int i = 0; i < count; i++) {
            imageResIds[i] = treeViewModels.get(i).getDrawableResId();
        }
        adapter.setImages(imageResIds);
        onPageSelected(0);
    }

    void bindUi(View view) {
        descriptionLabel = (TextView) view.findViewById(R.id.description);
        widthLabel = (TextView) view.findViewById(R.id.width);
        heightLabel = (TextView) view.findViewById(R.id.height);
        leafLabel = (TextView) view.findViewById(R.id.leaf);
        shapeLabel = (TextView) view.findViewById(R.id.shape);

        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });

        pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        pager.setOffscreenPageLimit(1);
    }

    void initBlurredBackground(View view) {
        Drawable blurredBackground = presenter.getBlurredBackground(getContext(), R.drawable.bg_forrest);
        view.setBackground(blurredBackground);
    }

    void nextFragment() {
        pageListener.next();
    }

    void bindTreeViewModel(TreeViewModel viewModel) {
        TreeDescription tree = viewModel.getTree();
        descriptionLabel.setText(tree.getDescription());
        String width = String.format("%s - %s",tree.getMinWidth(), tree.getMaxWidth());
        String height = String.format("%s - %s",tree.getMinHeight(), tree.getMaxHeight());
        widthLabel.setText(width);
        heightLabel.setText(height);
        shapeLabel.setText(tree.getShape());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TreeViewModel treeViewModel = presenter.getTreeViewModel(position);
        bindTreeViewModel(treeViewModel);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
