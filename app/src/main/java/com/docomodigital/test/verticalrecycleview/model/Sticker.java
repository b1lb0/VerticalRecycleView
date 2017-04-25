package com.docomodigital.test.verticalrecycleview.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.docomodigital.test.verticalrecycleview.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by matteo on 29/01/15.
 */
public class Sticker {
    //   final int FRAMEDURATION = Resources.getSystem().getInteger(R.integer.fps);
    public final int FRAMEDURATION = 40;
    public String label;
    public int width;
    public int height;
    public boolean isMemoryOptimized = true;
    private int max;
    private String bundleDirectory = null;
    private boolean has_animation = true;
    private File sound_file = null;
    private File drawable_file = null;
    private ArrayList<File> animation_frames_filenames = null;
    private float scaleFactor = 1;
    private ThumbRect info_on_scene = new ThumbRect(); //position and size

    public Sticker(String label, ThumbRect thumbRect, String path) throws Exception {
        this.label = label;
        this.info_on_scene = thumbRect;
        setBundleDirectory(path);
    }

    // this function parse the file tree of the sticker and collect all the information required to work
    private void collectInfo() throws Exception {
        //info_on_scene = new ThumbRect(); //initialization
        animation_frames_filenames = new ArrayList<File>();
        //isMemoryOptimized = true;

        //check directory..
        File stickerDirectory = getStickerDirectory();
        if ((stickerDirectory == null) || !stickerDirectory.exists() || !stickerDirectory.isDirectory()) {
            Log.w(getClass().getSimpleName(), "collectInfo:: invalid sticker bundle directory! " + stickerDirectory);
            return;
        }

        File[] files = getStickerDirectory().listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File object1, File object2) {
                return object1.getName().compareTo(object2.getName());
            }
        });

        for (File file : files) {
             if (file.getName().matches(".*000.png")) {
//                    has_animation = false;
                    drawable_file = file;
//                    animation_frames_filenames.add(file);
             } else if (file.getName().matches(".*.png")) {
                    has_animation = false;
                    if (drawable_file == null) {
                        drawable_file = file;
                    } else {
                        has_animation = true;
                    }
                    animation_frames_filenames.add(file);
             } else {
                if (file.getName().matches(".*.mp3")) {
                    sound_file = file;
                }
            }
        }
        if (drawable_file != null) {
            Log.d(getClass().getSimpleName(), "collectInfo:: successfully completed. " +
                    "files found=" + files.length + ", label=" + label + " " +
                    "(has_animation=" + has_animation +
                    ", frames=" + animation_frames_filenames.size() +
                    ", has sound=" + (sound_file != null) +
                    ")");
        } else {
            Log.w(getClass().getSimpleName(), "collectInfo:: error during initialization!");
            throw new Exception("initialization error: is Bundle correct?");
        }
    }

    public Drawable getFirstDrawable()  {
        return getResizedDrawable(drawable_file);
    }

    public File getDrawableFile() {
        return drawable_file;
    }

    public String getLabel() {
        return label;
    }
    public int getMax() {
        return max;
    }

    public int getX() {
        return info_on_scene.x;
    }

    public int getY() {
        return info_on_scene.y;
    }

    public int getWidth() {
        return info_on_scene.width;
    }

    public int getHeight() {
        return info_on_scene.height;
    }
    //-----

    private Drawable getResizedDrawable(File f) {
        Bitmap originalBitmap = BitmapFactory.decodeFile(f.getPath());
        float resizedFactor = 2f * scaleFactor;
        //Log.i(getClass().getSimpleName(), "getResizedDrawable: density=" + density);
        return Utils.scaleImage(new BitmapDrawable(originalBitmap), isMemoryOptimized ? scaleFactor / resizedFactor : scaleFactor);
        //return new BitmapDrawable(croppedBitmap);
    }

    public AnimationDrawable getAnimation() {
        if (gotAnimation()) {
            AnimationDrawable animation = new AnimationDrawable();
            Log.d(getClass().getSimpleName(), "getAnimation:: ready to load " + animation_frames_filenames.size() + " frames");
            int cont = 0;
            for (File file : animation_frames_filenames) {
                Drawable d = getResizedDrawable(file);

                cont++;
                Log.d("Sticker:", getLabel() + " single frame " + cont + " width: " + d.getIntrinsicWidth() + " height: " + d.getIntrinsicHeight());

                animation.addFrame(d, FRAMEDURATION);
            }

            return animation;
        } else return null;
    }

    public Drawable getAnimationFrame() {
        int id = 0;
        return getAnimationFrame(id);
    }

    public Drawable getAnimationFrame(int id) {
        if(has_animation==false) return null;
        return getResizedDrawable(animation_frames_filenames.get(id));
    }

    public boolean gotAnimation() throws NullPointerException {
        if (bundleDirectory != null) {
            return has_animation;
        }
        throw new NullPointerException("bundleDirectory is null");
    }

    public File getStickerDirectory() {
        return new File(bundleDirectory + "/" + label);
    }


    public File getSoundFile() {
        return sound_file;
    }

    public String getBundleDirectory() {
        return bundleDirectory;
    }

    public void setBundleDirectory(String dirname) throws Exception {
        bundleDirectory = dirname;
        collectInfo();
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
