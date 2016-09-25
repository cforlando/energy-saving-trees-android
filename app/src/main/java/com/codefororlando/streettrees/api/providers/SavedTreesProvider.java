package com.codefororlando.streettrees.api.providers;

import android.content.Context;
import android.os.AsyncTask;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.models.TreeJsonModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedTreesProvider {
    private Map<LatLng, Tree> treeCache;

    private static SavedTreesProvider instance;

    public static SavedTreesProvider getInstance(Context context) throws IOException, ParseException {
        if (instance == null) {
            instance = new SavedTreesProvider(context);
        }
        return instance;
    }

    private SavedTreesProvider(Context context) throws IOException, ParseException {
        treeCache = new HashMap<>();
        PopulateCache(context);
    }

    public Tree[] getVisibleTrees(final VisibleRegion region, int limit) {
        // TODO: When the API gets fixed, call the API in addition to using the internal cache
        // TODO: Find a better data structure than just iterating over a list
        // TODO: Use an AsyncTask
        List<Tree> trees = new ArrayList<>();

        int count = 0;
        for (Map.Entry<LatLng, Tree> entry : treeCache.entrySet()) {
            if(count >= limit) {
                break;
            }

            LatLng loc = entry.getKey();
            Tree tree = entry.getValue();
            if (region.latLngBounds.contains(loc)) {
                trees.add(tree);
                count++;
            }
        }
        return trees.toArray(new Tree[0]);
    }

//    public Tree getTreeDetails(int orderId) {
//
//    }

    private void PopulateCache(Context context) throws IOException, ParseException {
        InputStream is = context.getResources().openRawResource(R.raw.data);
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
        TreeJsonModel[] trees = gson.fromJson(jsonString, TreeJsonModel[].class);
        for (TreeJsonModel tree : trees) {
            Tree treeModel = new Tree();

            treeModel.setOrder(Integer.parseInt(tree.orderId));

            String[] latLng = tree.location.split(", ");
            treeModel.setLocation(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));

            treeModel.setTreeName(tree.treeName);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            treeModel.setDate(dateFormat.parse(tree.date));

            LatLng point = treeModel.getLocation();
            treeCache.put(point, treeModel);
        }
    }

}
