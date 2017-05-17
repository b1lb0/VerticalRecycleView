package com.docomodigital.test.verticalrecycleview.MapView.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by matteo on 29/01/15.
 */
public class Item {
    private int FRAME_DURATION_MILLISEC = 40;
    private AnimationDrawable animation;
    private String label;
    private String animationDirectory = null;
    private File drawable_file = null;
    private ArrayList<File> animation_frames_filenames = null;
    private Position info_on_scene = new Position(); //position and size on the scene

    public Item(String label, Position thumbRect, String path, int millisec) throws Exception {
        this.label = label;
        this.info_on_scene = thumbRect;
        FRAME_DURATION_MILLISEC = millisec;
        setAnimationDirectory(path);
        getAnimation();
    }

    // this function parse the file tree of the animated item and collect all the information
    private void collectInfo()  {
        animation_frames_filenames = new ArrayList<>();

        try {
            File[] files = getAnimationDirectory().listFiles();
            Arrays.sort(files, (object1, object2) -> object1.getName().compareTo(object2.getName()));

            for (File file : files) {
                if (file.getName().matches(".*.png")) {
                    if (drawable_file == null) {
                        drawable_file = file;
                    }
                    animation_frames_filenames.add(file);

                }
            }
        } catch (Exception e) {
            Log.e("collectInfo","label:"+label+ " getAnimationDirectory():"+getAnimationDirectory());
        }
    }

    public Drawable getFirstDrawable() {
        return getResizedDrawable(drawable_file);
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

    private Drawable getResizedDrawable(File f) {
        if (f == null || !f.exists()) return null;
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        return new BitmapDrawable(getScaledBitmap(BitmapFactory.decodeFile(f.getPath(), option),0.5f));
    }

    private Bitmap getScaledBitmap(Bitmap bitmap, float scaleFactor) {
        return bitmap;
    }

    public AnimationDrawable getAnimation() {
        return getAnimation(true);
    }

    public AnimationDrawable getAnimation(boolean async) {
        if (animation != null) return animation;

        if (async)
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    readAnimationFromFile();

                    return null;
                }

            }.execute();
        else readAnimationFromFile();


        return animation;
    }

    private void readAnimationFromFile() {
        if (animation != null) return;
        AnimationDrawable animationFromFiles = new AnimationDrawable();
        for (File file : animation_frames_filenames) {
            animationFromFiles.addFrame(getResizedDrawable(file), FRAME_DURATION_MILLISEC);
        }
        animation = animationFromFiles;
    }

    public boolean hasAnimation() throws NullPointerException {
        return (animation != null && !animation_frames_filenames.isEmpty());
    }

    public File getAnimationDirectory() {
        return new File(animationDirectory + "/" + label);
    }


    public void setAnimationDirectory(String dirname) throws Exception {
        animationDirectory = dirname;
        collectInfo();
    }
}
