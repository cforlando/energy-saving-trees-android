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

package com.codefororlando.streettrees.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.TreeApplication;
import com.codefororlando.streettrees.api.models.TreeDescription;
import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider;
import com.codefororlando.streettrees.fragments.DetailFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailListener {

    final String TAG = "DETAILACTIVITY";

    @Inject
    TreeDescriptionProvider treeDescriptionProvider;
    private LatLng location;
    private String treeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ((TreeApplication) getApplication()).getTreeProviderComponent().inject(this);

        if (savedInstanceState == null) {
            DetailFragment frag = DetailFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer, frag, DetailFragment.TAG)
                    .commit();
        }

        Intent loadingIntent = getIntent();
        location = loadingIntent.getParcelableExtra(MainActivity.EXTRA_LOCATION);
        treeType = loadingIntent.getStringExtra(MainActivity.EXTRA_TREETYPE);
    }

	@Override
	public TreeDescription getTreeData() {
		try {
			return treeDescriptionProvider.getTreeDescription(treeType);
		} catch(IllegalArgumentException iae){
			Toast badTreeMsg = Toast.makeText(this.getApplicationContext(),
					iae.getMessage(), Toast.LENGTH_SHORT);
			badTreeMsg.show();
			return null;
	}
}
}
