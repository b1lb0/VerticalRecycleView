package com.docomodigital.test.verticalrecycleview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by matteo on 03/02/15.
 */
public class Utils {
    static private LruCache<String, Drawable> mMemoryCache;

    public static int convertPixelToDp(int px) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        int dp = (int) ((float) px / density);
        Log.d("Utils", "convertPixelToDp: density=" + density + ", px=" + px + ", dp=" + dp);
        return dp;
    }

    public static int convertDpToPixel(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        int px = (int) (dp * density);
        Log.d("Utils", "convertDpToPixel: density=" + density + ", dp=" + dp + ", px=" + px);
        return px;
    }

    public static Drawable scaleImage(Drawable image, float scaleFactor) {
        if ((image == null) || !(image instanceof BitmapDrawable) || scaleFactor == 1) {
            Log.e("Utils", "scaleImage failed or ignored.");
            return image;
        }

        if (scaleFactor == 1) {
            Log.e("Utils", "scaleImage ignored.");
            return image;
        }

        Bitmap b = ((BitmapDrawable) image).getBitmap();

        int sizeX = Math.round((float) b.getWidth() * scaleFactor);
        int sizeY = Math.round((float) b.getHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, true);
        //b.recycle();
        b = null;

        image = new BitmapDrawable(Resources.getSystem(), bitmapResized);

        image.setCallback(null);
        //bitmapResized.recycle();
        //bitmapResized = null;

        return image;
    }

    public static Bitmap blurBitmap(Bitmap src, int radius, Context context) {
        Bitmap outBitmap = src.copy(src.getConfig(), true);

        RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, src, Allocation.MipmapControl.MIPMAP_NONE, 0);
        final Allocation output = Allocation.createFromBitmap(rs, outBitmap);

        final ScriptIntrinsicBlur script =
                ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(outBitmap);

        rs.destroy();
        rs = null;
        return outBitmap;
    }

    static public Bitmap addShadow(Bitmap croppedBitmap, float shadowScaleFactor, Context context) {
        //Fattore di ingrandimento dell'immagine per fare l'ombra
        shadowScaleFactor = 1.3f;
        int radius = 25;

        int wPixel = (int) (croppedBitmap.getWidth() * shadowScaleFactor) - croppedBitmap.getWidth();
        int hPixel = (int) (croppedBitmap.getHeight() * shadowScaleFactor) - croppedBitmap.getHeight();


        //Creo lo sticker più grande per fare da ombra
        Bitmap shadowBitmap = Bitmap.createBitmap(croppedBitmap.getWidth() + wPixel, croppedBitmap.getHeight() + hPixel, Bitmap.Config.ARGB_8888);
        Canvas canvasShadow = new Canvas(shadowBitmap);


        //Filtro per rendere l'immagine più grande ombra
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Il primo è il colore a cui convertire l'intera immagine, il secondo è quanto fare scuro
        paint.setColorFilter(new LightingColorFilter(Color.rgb(50, 50, 50), Color.rgb(100, 100, 100)));

        canvasShadow.drawBitmap(croppedBitmap, wPixel / 2, hPixel / 2, paint);
        shadowBitmap = Utils.blurBitmap(shadowBitmap, radius, context);

        //Creo la bitmap finale leggermente più grande
        Bitmap finalBitmap = Bitmap.createBitmap(croppedBitmap.getWidth() + wPixel, croppedBitmap.getHeight() + hPixel, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);

        //stampo l'immagine più grossa con il paint che la rende bianca e nera e con trasparenza
        canvas.drawBitmap(shadowBitmap, 0, 0, paint);
        shadowBitmap.recycle();
        shadowBitmap = null;


        canvas.drawBitmap(croppedBitmap, wPixel / 2, hPixel / 2, null);
        croppedBitmap.recycle();
        croppedBitmap = null;

        return finalBitmap;
    }

    static public float getScreenDensity() {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return density;
    }

    public static void setImageCache() {

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        /*final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        JsonProp.logi("MEMORY LRU", "" + cacheSize);
        */

        if (mMemoryCache == null) {
            int maxItems = 80;
            mMemoryCache = new LruCache<String, Drawable>(maxItems) {
            };
        }
    }

    public static void addBitmapToMemoryCache(String key, Drawable drawable) {
        if (getDrawableFromMemCache(key) == null) {
            mMemoryCache.put(key, drawable);
        }
    }

    public static Drawable getDrawableFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "KIM ALBUM");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return mediaStorageDir;
    }

    public void saveBitmap(Context context, File pictureFile, Bitmap rotatedBitmap, boolean notify) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;

            FileOutputStream fos = new FileOutputStream(pictureFile);
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);

            //fos.write(data);
            fos.close();

            //La inserisco nella galleria di android

            if (notify) {
                //recycle rotated bitmap (no need)
                //  rotatedBitmap.recycle();
                //rotatedBitmap.recycle();
                rotatedBitmap = null;
                MediaStore.Images.Media.insertImage(context.getContentResolver(), pictureFile.getParent() + pictureFile.separator + pictureFile.getName(), pictureFile.getName(), null);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // This snippet hides the system bars.
    public static void hideSystemUI(View mDecorView) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            mDecorView.setSystemUiVisibility(
                    //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    //        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

}
