package com.codefororlando.streettrees.util;

import com.codefororlando.streettrees.api.models.Tree;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.List;

public final class TreeMapUtil {
    public static List<Tree> getVisibleTrees(final VisibleRegion region, List<Tree> trees, int limit) {
        int count = 0;
        List<Tree> out = new ArrayList<>();
        for (Tree entry : trees) {
            if (count >= limit) {
                break;
            }

            LatLng loc = entry.getLocation();
            if (region.latLngBounds.contains(loc)) {
                out.add(entry);
                count++;
            }
        }
        return out;
    }
}
