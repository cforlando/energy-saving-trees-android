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
        if (!treeCache.containsKey(name)) {
            // TODO(jpr): alert user, log error, dont crash
            throw new IllegalArgumentException("Tree name not in the cache");
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
