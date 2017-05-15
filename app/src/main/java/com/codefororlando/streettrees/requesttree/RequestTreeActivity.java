// Copyright © 2017 Code for Orlando.
// 
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.codefororlando.streettrees.requesttree;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Address;
import com.codefororlando.streettrees.api.models.ContactInfo;
import com.codefororlando.streettrees.requesttree.contactinfo.ContactInfoFragment;
import com.codefororlando.streettrees.requesttree.deliveryaddress.AddressFormFragment;
import com.codefororlando.streettrees.requesttree.selecttree.SelectTreeFragment;
import com.codefororlando.streettrees.view.FragmentListPager;
import com.codefororlando.streettrees.view.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class RequestTreeActivity extends AppCompatActivity implements PageFragment.PageFragmentListener,
        AddressFormFragment.AddressFormListener,
        ContactInfoFragment.ContactInfoListener,
        RequestReviewFragment.ReviewFragmentDelegate {

    private static final List<FragmentListPager.Entry> pagerFragments = new ArrayList<>();

    private Address address;
    private ContactInfo contactInfo;

    private FragmentListPager listPager;
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
        if (currentItemIndex < FRAGMENT_COUNT) {
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
        if (currentItemIndex > 0) {
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
