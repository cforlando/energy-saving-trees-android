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

package com.codefororlando.streettrees.requesttree;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.codefororlando.streettrees.bitmap.BitmapBlurObservableTask;
import com.codefororlando.streettrees.view.PageFragment;

import rx.Observable;
import rx.functions.Action1;

public abstract class BlurredBackgroundFragment extends PageFragment {

    protected void initBlurredBackground(final View view, final @DrawableRes int drawableResId,
                                         float radius, float scale) {
        final Resources resources = getResources();
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(resources, drawableResId);
        Observable<Bitmap> blurTaskObservable = new BitmapBlurObservableTask.Builder(getContext(), backgroundBitmap)
                .radius(radius)
                .scale(scale)
                .build()
                .getObservable();
        blurTaskObservable.subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                Drawable blurredDrawable = new BitmapDrawable(resources, bitmap);
                view.setBackground(blurredDrawable);
            }
        });
    }
}
