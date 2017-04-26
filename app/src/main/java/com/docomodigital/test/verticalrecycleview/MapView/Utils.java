package com.docomodigital.test.verticalrecycleview.MapView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by matteo on 03/02/15.
 */
public class Utils {

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
}
