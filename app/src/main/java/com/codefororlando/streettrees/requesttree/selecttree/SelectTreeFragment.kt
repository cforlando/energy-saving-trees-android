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

package com.codefororlando.streettrees.requesttree.selecttree

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.codefororlando.streettrees.R
import com.codefororlando.streettrees.api.providers.TreeDescriptionProvider
import com.codefororlando.streettrees.requesttree.BlurredBackgroundFragment
import com.codefororlando.streettrees.util.TreeDrawableResourceProvider
import com.codefororlando.streettrees.view.ImageViewPagerAdapter
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance

class SelectTreeFragment : BlurredBackgroundFragment(),
        SelectTreePresenter.SelectTreeView,
        ViewPager.OnPageChangeListener,
        SupportFragmentInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private var nextButton: Button? = null
    private var pager: ViewPager? = null
    private var adapter: ImageViewPagerAdapter? = null

    private var treeNameLabel: TextView? = null
    private var descriptionLabel: TextView? = null
    private var widthLabel: TextView? = null
    private var heightLabel: TextView? = null
    private var leafLabel: TextView? = null
    private var shapeLabel: TextView? = null

    private var presenter: SelectTreePresenter? = null
    private var pageIndex: Int = 0

    private val treeDescriptionProvider: TreeDescriptionProvider by instance()
    private val treeDrawableResourceProvider: TreeDrawableResourceProvider = TreeDrawableResourceProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()

        adapter = ImageViewPagerAdapter(activity)
        presenter = SelectTreePresenter(treeDescriptionProvider, treeDrawableResourceProvider)
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.request_tree_select, container, false)
        pageIndex = savedInstanceState?.getInt(PAGER_INDEX_KEY, 0) ?: 0
        bindUi(view)
        initBlurredBackground(view, R.drawable.bg_forrest, BLUR_RADIUS, BLUR_SCALE)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(PAGER_INDEX_KEY, pager!!.currentItem)
    }

    override fun onStart() {
        super.onStart()
        activity.title = getString(R.string.select_tree_fragment_title)
        presenter!!.attach(this)
        presenter!!.loadTreeDescriptions()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.detach()
    }

    override fun present(treeViewModels: List<TreeViewModel>) {
        val count = treeViewModels.size
        val imageResIds = IntArray(count)
        for (i in 0..count - 1) {
            imageResIds[i] = treeViewModels[i].drawableResId
        }
        adapter!!.setImages(imageResIds)
        onPageSelected(0)
    }

    internal fun bindUi(view: View) {
        treeNameLabel = view.findViewById(R.id.tree_name) as TextView
        descriptionLabel = view.findViewById(R.id.description) as TextView
        widthLabel = view.findViewById(R.id.width) as TextView
        heightLabel = view.findViewById(R.id.height) as TextView
        leafLabel = view.findViewById(R.id.leaf) as TextView
        shapeLabel = view.findViewById(R.id.shape) as TextView

        nextButton = view.findViewById(R.id.next_button) as Button
        nextButton!!.setOnClickListener { nextFragment() }

        pager = view.findViewById(R.id.view_pager) as ViewPager
        pager!!.adapter = adapter
        pager!!.addOnPageChangeListener(this)
        pager!!.offscreenPageLimit = 1
    }

    internal fun nextFragment() {
        pageListener.next()
    }

    internal fun bindTreeViewModel(viewModel: TreeViewModel) {
        val tree = viewModel.tree
        val width = String.format("%s - %s", tree.minWidth, tree.maxWidth)
        val height = String.format("%s - %s", tree.minHeight, tree.maxHeight)

        treeNameLabel!!.text = tree.name
        descriptionLabel!!.text = tree.description
        widthLabel!!.text = width
        heightLabel!!.text = height
        leafLabel!!.text = tree.leaf
        shapeLabel!!.text = tree.shape
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        val treeViewModel = presenter!!.getTreeViewModel(position)
        if (treeViewModel != null) {
            bindTreeViewModel(treeViewModel)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}

    companion object {
        private const val PAGER_INDEX_KEY = "PAGER_INDEX_KEY"
        private const val BLUR_RADIUS = 25f
        private const val BLUR_SCALE = 0.05f
    }

}
