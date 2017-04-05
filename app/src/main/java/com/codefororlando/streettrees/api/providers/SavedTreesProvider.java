package com.codefororlando.streettrees.api.providers;

import android.content.Context;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.models.TreeJsonModel;
import com.google.android.gms.maps.model.LatLng;
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
import java.util.HashMap;
import java.util.Map;

public class SavedTreesProvider {
    private final Map<LatLng, Tree> treeCache;

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

    private void PopulateCache(Context context) throws IOException, ParseException {
        String jsonString = getBakedApiResponse(context);
        Gson gson = new GsonBuilder().create();
        TreeJsonModel[] trees = gson.fromJson(jsonString, TreeJsonModel[].class);
        for (TreeJsonModel treeModel : trees) {
            Tree tree =  buildTreeFromTreeModel(treeModel);
            treeCache.put(tree.getLocation(), tree);
        }
    }

    private String getBakedApiResponse( Context context) throws IOException {
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

        return writer.toString();
    }

    private Tree buildTreeFromTreeModel( TreeJsonModel treeModel ) throws ParseException {
        Tree tree = new Tree();

        tree.setOrder(Integer.parseInt(treeModel.orderId));

        String[] latLng = treeModel.location.split(", ");
        tree.setLocation(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));

        tree.setTreeName(treeModel.treeName);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        tree.setDate(dateFormat.parse(treeModel.date));

        return tree;
    }

}
