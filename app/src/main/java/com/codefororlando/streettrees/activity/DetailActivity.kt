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

package com.codefororlando.streettrees.activity

import android.os.Bundle
import com.codefororlando.streettrees.R
import com.codefororlando.streettrees.api.models.TreeDescription
import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider
import com.codefororlando.streettrees.fragments.DetailFragment
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import com.google.android.gms.maps.model.LatLng

class DetailActivity : KodeinAppCompatActivity(), DetailFragment.DetailListener {

    private val treeDescriptionProvider: TreeDescriptionProvider by instance()

    private lateinit var location: LatLng
    private lateinit var treeType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            val frag = DetailFragment.newInstance()
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, frag, DetailFragment::class.java.name)
                    .commit()
        }

        val loadingIntent = intent
        location = loadingIntent.getParcelableExtra<LatLng>(MainActivity.EXTRA_LOCATION)
        treeType = loadingIntent.getStringExtra(MainActivity.EXTRA_TREETYPE)
    }

    override val treeData: TreeDescription
        get() = treeDescriptionProvider.getTreeDescription(treeType)

}
