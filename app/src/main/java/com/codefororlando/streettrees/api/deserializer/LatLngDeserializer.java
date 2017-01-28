package com.codefororlando.streettrees.api.deserializer;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by johnli on 9/25/16.
 */
public class LatLngDeserializer implements JsonDeserializer<LatLng> {

    @Override
    public LatLng deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jobject = json.getAsJsonObject();
        JsonArray jarr = jobject.get("coordinates").getAsJsonArray();
        double lng = jarr.get(1).getAsDouble();
        double lat = jarr.get(0).getAsDouble();
        return new LatLng(lng, lat);
    }
}