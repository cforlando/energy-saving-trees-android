// Copyright © 2017 Code for Orlando.
// 
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.codefororlando.streettrees.util;

import android.support.annotation.DrawableRes;

import com.codefororlando.streettrees.R;

import javax.inject.Inject;

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
