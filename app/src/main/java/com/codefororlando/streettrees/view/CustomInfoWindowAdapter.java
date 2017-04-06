<<<<<<< HEAD
package com.codefororlando.streettrees.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.util.TreeDrawableResourceProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * This class customizes the Google MAP info window contents.
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mContents;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        mContents = inflater.inflate(R.layout.custom_tree_info_contents, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        render(marker, mContents);
        return mContents;
    }

    private void render(Marker marker, View view) {
        int badge;
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (marker.getTitle() != null) {
            // TODO Find out why the Title is been receive from the Google MAP API with additional
            // empty spaces when the title length is less then 17. We are trimming in order to use it.
            String title = marker.getTitle().trim();
            titleUi.setText(title);

            badge = new TreeDrawableResourceProvider().getDrawable(title);

            // Setting the ImageView resource and maxHeight.
            ImageView imageView = (ImageView) view.findViewById(R.id.badge);
            imageView.setImageResource(badge);
            imageView.setMaxHeight(100);

        } else {
            titleUi.setText("");
        }
    }
}

=======
package com.codefororlando.streettrees.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.util.TreeDrawableResourceProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * This class customizes the Google MAP info window contents.
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mContents;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        mContents = inflater.inflate(R.layout.custom_tree_info_contents, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        render(marker, mContents);
        return mContents;
    }

    private void render(Marker marker, View view) {
        int badge;
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (marker.getTitle() != null) {
            // TODO Find out why the Title is been receive from the Google MAP API with additional
            // empty spaces when the title length is less then 17. We are trimming in order to use it.
            String title = marker.getTitle().trim();
            titleUi.setText(title);

            badge = new TreeDrawableResourceProvider().getDrawable(title);

            // Setting the ImageView resource and maxHeight.
            ImageView imageView = (ImageView) view.findViewById(R.id.badge);
            imageView.setImageResource(badge);
            imageView.setMaxHeight(100);

        } else {
            titleUi.setText("");
        }
    }
}

>>>>>>> 1bf24e5fd3cb3cfe3753799033ff4ade382ebc19
