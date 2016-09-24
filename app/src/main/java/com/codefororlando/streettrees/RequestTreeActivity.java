package com.codefororlando.streettrees;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codefororlando.streettrees.fragments.request_tree.AddressFormFragment;
import com.codefororlando.streettrees.fragments.request_tree.ConfirmRequestFragment;
import com.codefororlando.streettrees.fragments.request_tree.ContactInfoFragment;
import com.codefororlando.streettrees.fragments.request_tree.RequestIntroFragment;
import com.codefororlando.streettrees.fragments.request_tree.RequestReviewFragment;
import com.codefororlando.streettrees.fragments.request_tree.SelectTreeFragment;
import com.codefororlando.streettrees.view.NoSwipeViewPager;
import com.codefororlando.streettrees.view.PageFragment;

public class RequestTreeActivity extends AppCompatActivity implements PageFragment.PageFragmentListener {

    NoSwipeViewPager pager;
    RequestPagerAdapter pagerAdapter;

    PageFragment[] pagerFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_tree);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pagerFragments = new PageFragment[5];
        pagerFragments[0] = new RequestIntroFragment();
        pagerFragments[1] = new SelectTreeFragment();
        pagerFragments[2] = new AddressFormFragment();
        pagerFragments[3] = new ContactInfoFragment();
        pagerFragments[4] = new RequestReviewFragment();
        pagerFragments[5] = new ConfirmRequestFragment();

        pager = (NoSwipeViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new RequestPagerAdapter(getSupportFragmentManager(), pagerFragments);
        pager.setAdapter(pagerAdapter);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void next(Bundle bundle) {
        int currentItemIndex = pager.getCurrentItem();
        if(currentItemIndex < (pagerAdapter.getCount() - 1)) {
            pager.setCurrentItem(currentItemIndex + 1);
        }
    }

    @Override
    public void previous() {
        int currentItemIndex = pager.getCurrentItem();
        if(currentItemIndex > 0 ) {
            pager.setCurrentItem(currentItemIndex + 1);
        }
    }

    public static class RequestPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 6;

        PageFragment[] fragments;

        public RequestPagerAdapter(FragmentManager fragmentManager, PageFragment[] fragments) {
            super(fragmentManager);
            this.fragments = fragments;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
