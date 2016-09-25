package com.codefororlando.streettrees.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Address;
import com.codefororlando.streettrees.api.models.ContactInfo;
import com.codefororlando.streettrees.fragments.request_tree.AddressFormFragment;
import com.codefororlando.streettrees.fragments.request_tree.ConfirmRequestFragment;
import com.codefororlando.streettrees.fragments.request_tree.ContactInfoFragment;
import com.codefororlando.streettrees.fragments.request_tree.RequestIntroFragment;
import com.codefororlando.streettrees.fragments.request_tree.RequestReviewFragment;
import com.codefororlando.streettrees.fragments.request_tree.SelectTreeFragment;
import com.codefororlando.streettrees.view.FragmentListPager;
import com.codefororlando.streettrees.view.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class RequestTreeActivity extends AppCompatActivity implements PageFragment.PageFragmentListener,
        AddressFormFragment.AddressFormListener,
        ContactInfoFragment.ContactInfoListener,
        RequestReviewFragment.ReviewFragmentDelegate {

    List<FragmentListPager.Entry> pagerFragments = new ArrayList<>();

    Address address;
    ContactInfo contactInfo;

    FragmentListPager listPager;
    private final int FRAGMENT_COUNT = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_tree);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pagerFragments.add(new FragmentListPager.Entry(RequestIntroFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(SelectTreeFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(AddressFormFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(ContactInfoFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(RequestReviewFragment.class));
        pagerFragments.add(new FragmentListPager.Entry(ConfirmRequestFragment.class));

        listPager = new FragmentListPager(getSupportFragmentManager(), android.R.id.content, pagerFragments, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void next() {
        int currentItemIndex = listPager.getCurrentItem();
        if(currentItemIndex < FRAGMENT_COUNT) {
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
        } else {
            finish();
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
}
