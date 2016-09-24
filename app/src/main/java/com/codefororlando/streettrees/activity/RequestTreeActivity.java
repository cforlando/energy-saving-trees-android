package com.codefororlando.streettrees.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Address;
import com.codefororlando.streettrees.api.models.ContactInfo;
import com.codefororlando.streettrees.api.models.Location;
import com.codefororlando.streettrees.fragments.request_tree.AddressFormFragment;
import com.codefororlando.streettrees.fragments.request_tree.ConfirmRequestFragment;
import com.codefororlando.streettrees.fragments.request_tree.ContactInfoFragment;
import com.codefororlando.streettrees.fragments.request_tree.RequestIntroFragment;
import com.codefororlando.streettrees.fragments.request_tree.RequestReviewFragment;
import com.codefororlando.streettrees.fragments.request_tree.SelectTreeFragment;
import com.codefororlando.streettrees.view.FragmentListPager;
import com.codefororlando.streettrees.view.NoSwipeViewPager;
import com.codefororlando.streettrees.view.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class RequestTreeActivity extends AppCompatActivity implements PageFragment.PageFragmentListener,
        AddressFormFragment.AddressFormListener,
        ContactInfoFragment.ContactInfoListener,
        RequestReviewFragment.ReviewFragmentDelegate {

    NoSwipeViewPager pager;
    RequestPagerAdapter pagerAdapter;

    List<FragmentListPager.Entry> pagerFragments = new ArrayList<>();

    Address address;
    ContactInfo contactInfo;

    FragmentListPager listPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_tree);

        pagerFragments.add(new FragmentListPager.Entry(RequestIntroFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(SelectTreeFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(AddressFormFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(ContactInfoFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(RequestReviewFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(ConfirmRequestFragment.class));

        listPager = new FragmentListPager(getSupportFragmentManager(), android.R.id.content, pagerFragments, null);
        //pager = (NoSwipeViewPager) findViewById(R.id.view_pager);
        //pagerAdapter = new RequestPagerAdapter(getSupportFragmentManager(), pagerFragments);
        //pager.setAdapter(pagerAdapter);
        //pager.setOffscreenPageLimit(1);
    }

    @Override
    public void next() {
        int currentItemIndex = listPager.getCurrentItem();
        if(currentItemIndex < (5)) {
            listPager.nextFragment(null);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        previous();
    }

    @Override
    public void previous() {
        int currentItemIndex = listPager.getCurrentItem();
        if(currentItemIndex > 0 ) {
            listPager.previousFragment();
        }
    }

    @Override
    public void onFormFilled(Address address) {
        this.address = address;
    }

    @Override
    public void onFormFilled(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public ContactInfo getContactInfo() {
        return contactInfo;
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
