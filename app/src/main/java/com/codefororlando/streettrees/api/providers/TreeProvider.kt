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
import com.codefororlando.streettrees.api.TreeAPIService
import com.codefororlando.streettrees.api.deserializer.LatLngDeserializer
import com.codefororlando.streettrees.api.deserializer.ResponseSanitizer
import com.codefororlando.streettrees.api.models.Tree
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.functions.Func0
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class TreeProvider(context: Context) : ITreeProvider {

    private val service: TreeAPIService

    init {
        val httpCacheDirectory = File(context.cacheDir, "cached_responses")
        val cache = Cache(httpCacheDirectory, CACHE_SIZE)
        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .registerTypeAdapter(LatLng::class.java, LatLngDeserializer())
                .registerTypeAdapterFactory(ResponseSanitizer())
                .create()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(client)
                .build()

        service = retrofit.create(TreeAPIService::class.java)
    }

    override fun getTrees(handler: ITreeProvider.TreeProviderResultHandler<Tree>) {
        val getTreesTransaction = service.trees
        getTreesTransaction.enqueue(object : Callback<List<Tree>> {
            override fun onResponse(call: Call<List<Tree>>, response: Response<List<Tree>>) {
                handler.onComplete(true, response.body()!!)
            }

            override fun onFailure(call: Call<List<Tree>>, t: Throwable) {
                handler.onComplete(false, listOf())
            }
        })
    }

    private val trees: List<Tree>
        @Throws(IOException::class)
        get() = service.trees
                .execute()
                .body() ?: listOf()

    override val treesObservable: Observable<List<Tree>>
        get() = Observable.defer(Func0 {
            try {
                return@Func0 Observable.just<List<Tree>>(trees)
            } catch (e: IOException) {
                return@Func0 Observable.error<List<Tree>>(e)
            }
        })

    companion object {
        private val BASE_URL = "https://brigades.opendatanetwork.com/resource/"
        private val CONNECT_TIMEOUT = 15
        private val WRITE_TIMEOUT = 30
        private val READ_TIMEOUT = 30
        private val CACHE_SIZE = (4 * 1024 * 1024).toLong() // 10 MB
    }
}

val treeProviderModule = Kodein.Module {
    bind<ITreeProvider>() with singleton { TreeProvider(instance()) }
}
