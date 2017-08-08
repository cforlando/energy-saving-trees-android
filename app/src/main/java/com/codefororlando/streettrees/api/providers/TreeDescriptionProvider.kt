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

package com.codefororlando.streettrees.api.providers

import android.content.Context
import com.codefororlando.streettrees.R
import com.codefororlando.streettrees.api.models.TreeDescription
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.GsonBuilder
import java.util.*

class TreeDescriptionProvider(context: Context) {
    private val treeCache: MutableMap<String, TreeDescription>

    init {
        treeCache = HashMap<String, TreeDescription>()
        populateCache(context)
    }

    fun getTreeDescription(name: String): TreeDescription {
        return treeCache[name]!!
    }

    val allTreeDescriptions: List<TreeDescription>
        get() = ArrayList(treeCache.values)

    private fun populateCache(context: Context) {
        Thread(object : Runnable {

            override fun run() {
                GsonBuilder().create()
                        .fromJson(getBakedApiResponse(), Array<TreeDescription>::class.java)
                        .forEach { tree ->
                            treeCache.put(tree.name, tree)
                        }
            }

            private fun getBakedApiResponse()
                    = context.resources
                    .openRawResource(R.raw.descriptions)
                    .reader(Charsets.UTF_8)
                    .readText()

        }).run()

    }

}

val treeDescriptionProviderModule = Kodein.Module {
    bind<TreeDescriptionProvider>() with singleton { TreeDescriptionProvider(instance()) }
}
