package com.example.qq.mycoordinatordemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class ViewUtils {
    private static final Canvas mCanvas = new Canvas();
    private static Random mRandom = new Random();
    public static int dp2px(Context context,int value){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }
    public static int randomIntPositive(int max, int min){
        return mRandom.nextInt(max - min) + min;
    }
    public static Bitmap createBitmapFromView(View view){
        if (view instanceof ImageView){
            Drawable drawable = ((ImageView)view).getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable){
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888,1);
        if (bitmap != null){
            synchronized (mCanvas){
                Canvas canvas = mCanvas;
                canvas.setBitmap(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }
    public static Bitmap createBitmapSafely(int width,int height,Bitmap.Config config,int retryCount){
        try {
            return Bitmap.createBitmap(width,height,config);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
            if (retryCount > 0){
                System.gc();
                return createBitmapSafely(width,height,config,retryCount - 1);
            }
            return null;
        }
    }
}
