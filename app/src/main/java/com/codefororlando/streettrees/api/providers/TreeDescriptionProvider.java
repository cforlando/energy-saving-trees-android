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

package com.codefororlando.streettrees.api.providers;

import android.content.Context;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.TreeDescription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeDescriptionProvider {
    private Map<String, TreeDescription> treeCache;

    public TreeDescriptionProvider(Context context) {
        treeCache = new HashMap<>();
        populateCache(context);
    }

    public TreeDescription getTreeDescription(String name) {
        name = name.trim();
        if (!treeCache.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Tree name not in the cache [%s]",name));
        }
        return treeCache.get(name);
    }

    public List<TreeDescription> getAllTreeDescriptions() {
        return new ArrayList<>(treeCache.values());
    }

    private void populateCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = getBakedApiResponse(context);
                Gson gson = new GsonBuilder().create();
                TreeDescription[] trees = gson.fromJson(jsonString, TreeDescription[].class);
                for (TreeDescription tree : trees) {
                    treeCache.put(tree.getName(), tree);
                }
            }

            private String getBakedApiResponse( Context context) {
                InputStream is = context.getResources().openRawResource(R.raw.descriptions);
                Writer writer = new StringWriter();
                char[] buffer = new char[1024];
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return writer.toString();
            }
        }).run();

    }
}
