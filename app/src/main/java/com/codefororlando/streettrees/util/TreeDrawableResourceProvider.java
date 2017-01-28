package com.codefororlando.streettrees.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.codefororlando.streettrees.R;

import javax.inject.Inject;

import static com.codefororlando.streettrees.R.drawable.cfo_yellow_trumpet;

/**
 * Created by johnli on 12/10/16.
 */

public class TreeDrawableResourceProvider {

    public
    @Inject
    TreeDrawableResourceProvider() {
    }

    public
    @DrawableRes
    int getDrawable(final String treeName) {
        switch (treeName) {
            case "Live Oak":
                return R.drawable.cfo_live_oak;
            case "Nuttall Oak":
                return R.drawable.cfo_nuttal_oak;
            case "Southern Magnolia":
                return R.drawable.cfo_magnolia;
            case "Winged Elm":
                return R.drawable.cfo_elm;
            case "Crape Myrtle":
                return R.drawable.cfo_myrtle;
            case "Eagleston Holly":
                return R.drawable.cfo_eagleston_holly;
            case "Chinese Pistache":
                return R.drawable.cfo_chinese_pistache;
            case "Dahoon Holly":
                return R.drawable.cfo_dahoon_holly;
            case "Tuliptree":
                return R.drawable.cfo_tulip_popular;
            case "Tabebuia Ipe":
            default:
                //TODO Use a question mark or no picture found image.
                return R.drawable.cfo_yellow_trumpet;
        }
    }
}
