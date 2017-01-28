package com.codefororlando.streettrees.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.bitmap.BitmapUtil;

/**
 * Created by johnli on 10/16/16.
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    int[] imageResIds;

    public ImageViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (imageResIds == null) {
            return 0;
        }
        int count = imageResIds.length;
        return count;
    }

    public void setImages(Resources res, int imageArrResId) {
        TypedArray typedArray = res.obtainTypedArray(imageArrResId);
        int count = res.getIntArray(imageArrResId).length;  //HACK, didn't find a better way to get length
        imageResIds = new int[count];
        for (int i = 0; i < count; i++) {
            int imageResId = typedArray.getResourceId(i, -1);
            if (imageResId != -1) {
                imageResIds[i] = imageResId;
            }
        }
        typedArray.recycle();
        notifyDataSetChanged();
    }

    public void setImages(int[] images) {
        imageResIds = images;
        notifyDataSetChanged();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        int imageRes = imageResIds[position];

        //REFACTOR: Move me out of here!
        BitmapFactory.Options onlyBoundsOptions = BitmapUtil.getOptions(container.getResources(), imageRes);
        BitmapFactory.Options bitmapCompressOptions = new BitmapFactory.Options();
        bitmapCompressOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmapCompressOptions.inSampleSize = BitmapUtil.calculateInSampleSize(onlyBoundsOptions, 320, 320);

        Bitmap bitmap = BitmapFactory.decodeResource(container.getResources(), imageRes, bitmapCompressOptions);
        imageView.setImageBitmap(bitmap);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}