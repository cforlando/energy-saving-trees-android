package com.codefororlando.streettrees.api.providers;

import android.content.Context;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.TreeDescription;
import com.codefororlando.streettrees.api.models.TreeDescriptionJsonModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class TreeDescriptionProvider {
    private Map<String, TreeDescription> treeCache;

    public TreeDescriptionProvider(Context context) {
        treeCache = new HashMap<>();
        populateCache(context);
    }

    public TreeDescription getTreeDescription(String name) {
        if (!treeCache.containsKey(name)) {
            throw new IllegalArgumentException("Tree name not in the cache");
        }
        return treeCache.get(name);
    }

    private void populateCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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

                String jsonString = writer.toString();
                Gson gson = new GsonBuilder().create();
                TreeDescriptionJsonModel[] trees = gson.fromJson(jsonString, TreeDescriptionJsonModel[].class);
                for (TreeDescriptionJsonModel tree : trees) {
                    TreeDescription model = new TreeDescription();
                    model.setName(tree.Tree);
                    model.setDescription(tree.Description);
                    model.setMinHeight(tree.Height);
                    model.setMinWidth(tree.Width);
                    model.setLeaf(tree.Leaf);
                    model.setShape(tree.Shape);
                    model.setMoisture(tree.Moiture);
                    model.setSunlight(tree.Sunlight);
                    model.setSoil(tree.Soil);
                    treeCache.put(model.getName(), model);
                }
            }
        }).run();

    }
}
