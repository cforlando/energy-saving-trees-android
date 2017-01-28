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
