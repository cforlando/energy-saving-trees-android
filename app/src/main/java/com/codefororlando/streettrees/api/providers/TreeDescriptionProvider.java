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

    private static TreeDescriptionProvider instance;

    public static TreeDescriptionProvider getInstance(Context context) throws IOException, ParseException {
        if (instance == null) {
            instance = new TreeDescriptionProvider(context);
        }
        return instance;
    }

    private TreeDescriptionProvider(Context context) throws IOException, ParseException {
        treeCache = new HashMap<>();
        PopulateCache(context);
    }

    public TreeDescription getTreeDescription(String description) {
        if (!treeCache.containsKey(description)) {
            throw new IllegalArgumentException("Tree name not in the cache");
        }
        return treeCache.get(description);
    }

    private void PopulateCache(Context context) throws IOException, ParseException {
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
            is.close();
        }

        String jsonString = writer.toString();
        Gson gson = new GsonBuilder().create();
        TreeDescriptionJsonModel[] trees = gson.fromJson(jsonString, TreeDescriptionJsonModel[].class);
        for (TreeDescriptionJsonModel tree : trees) {
            TreeDescription model = new TreeDescription();

            model.setTreeName(tree.Tree);
            model.setDescription(tree.Description);
            model.setHeight(tree.Height);
            model.setWidth(tree.Width);
            model.setLeaf(tree.Leaf);
            model.setShape(tree.Shape);
            model.setMoisture(tree.Moiture);
            model.setSunlight(tree.Sunlight);
            model.setSoil(tree.Soil);

            treeCache.put(model.getTreeName(), model);
        }
    }
}
