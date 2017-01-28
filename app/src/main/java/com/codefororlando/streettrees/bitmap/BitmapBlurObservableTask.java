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

    private Context context;
    private Bitmap bitmap;
    private float scale;
    private float radius;


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
        private Context context;
        private Bitmap bitmap;
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
