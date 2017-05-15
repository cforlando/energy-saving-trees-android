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

package com.codefororlando.streettrees.bitmap;

import android.content.Context;
import android.graphics.Bitmap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class BitmapBlurObservableTask {

    private final static float DEFAULT_BLUR_SCALE = .05f;
    private final static float DEFAULT_BLUR_RADIUS = 25f;

    private final Context context;
    private final Bitmap bitmap;
    private final float scale;
    private final float radius;


    private BitmapBlurObservableTask(Builder builder) {
        context = builder.context;
        bitmap = builder.bitmap;
        scale = builder.scale;
        radius = builder.radius;
    }

    public Observable<Bitmap> getObservable() {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap blurredBackground = BlurBuilder.blur(context, bitmap, scale, radius);
                subscriber.onNext(blurredBackground);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static class Builder {
        private final Context context;
        private final Bitmap bitmap;
        private float scale = DEFAULT_BLUR_SCALE;
        private float radius = DEFAULT_BLUR_RADIUS;

        public Builder(Context context, Bitmap bitmap) {
            this.context = context;
            this.bitmap = bitmap;
        }

        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        public BitmapBlurObservableTask build() {
            return new BitmapBlurObservableTask(this);
        }
    }

}
