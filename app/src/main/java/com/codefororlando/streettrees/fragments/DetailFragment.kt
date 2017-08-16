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

package com.codefororlando.streettrees.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.codefororlando.streettrees.R
import com.codefororlando.streettrees.api.models.TreeDescription

class DetailFragment : Fragment() {

    private var activityListener: DetailListener? = null
    private var layoutView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailListener) {
            activityListener = context
        } else {
            throw IllegalArgumentException("Cannot attach DetailFragment to Activity without DetailListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(R.layout.fragment_detail_activity, container, false)
        return layoutView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val treeDescription = activityListener!!.treeData

        (layoutView!!.findViewById(R.id.treeHeight) as TextView).text = treeDescription.minHeight
        (layoutView!!.findViewById(R.id.treeLeaf) as TextView).text = treeDescription.leaf
        (layoutView!!.findViewById(R.id.treeShape) as TextView).text = treeDescription.shape
        (layoutView!!.findViewById(R.id.treeWidth) as TextView).text = treeDescription.minWidth
        (layoutView!!.findViewById(R.id.treeSunlight) as TextView).text = treeDescription.sunlight
        (layoutView!!.findViewById(R.id.treSoil) as TextView).text = treeDescription.soil
        (layoutView!!.findViewById(R.id.treeMoisture) as TextView).text = treeDescription.moisture
        (layoutView!!.findViewById(R.id.treeDescription) as TextView).text = treeDescription.description
        (layoutView!!.findViewById(R.id.treeType) as TextView).text = treeDescription.name
    }

    interface DetailListener {
        val treeData: TreeDescription
    }

    companion object {

        fun newInstance(): DetailFragment {
            val frag = DetailFragment()

            val args = Bundle()
            frag.arguments = args

            return frag
        }
    }

}
