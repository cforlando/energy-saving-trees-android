package com.codefororlando.streettrees.api.providers;

import android.content.Context;

import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;
import com.codefororlando.streettrees.R;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by johnli on 9/25/16.
 */
public class GeoProvider {

    private void PopulateCache(Context context) throws IOException, ParseException {
        InputStream is = context.getResources().openRawResource(R.raw.data);

        try {
            GeoJSONObject geoJSON = GeoJSON.parse(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
