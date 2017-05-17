package com.docomodigital.test.verticalrecycleview.MapView.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

import com.docomodigital.test.verticalrecycleview.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tobia on 17/05/17.
 */

public class Stage {
    private final String resType;
    private final String filename;
    private List<Map> data;
    WeakReference<Context> contextRef;

    public Stage(Context context, String rawValue, String filenameValue){
        contextRef = new WeakReference<>(context);
        resType = rawValue;
        filename = filenameValue;
    }

    public List<Map> fill_with_data() {
        data = new ArrayList<>();

        InputStream zipInputStream = contextRef.get().getResources().openRawResource(
                contextRef.get().getResources().getIdentifier(filename,
                        resType, contextRef.get().getPackageName()));

        String root_animation_dir = Environment.getExternalStorageDirectory().getPath()+"/";

        if (!new File(root_animation_dir+"/animation").exists()) {
            ZipUtil.unpack(zipInputStream, new File(root_animation_dir));
        }

        float scale = (float) contextRef.get().getResources().getDisplayMetrics().widthPixels / (float) 1080 ;

        List<Item> items = new ArrayList<>();


        try {
            //LEVEL 3
            data.add(new Map("grade3", R.drawable.map3_0,(int)(1080*scale),(int)(1159*scale), items));
            data.add(new Map("grade3", R.drawable.map3_1,(int)(1080*scale),(int)(1159*scale), items));
            data.add(new Map("grade3", R.drawable.map3_2,(int)(1080*scale),(int)(1159*scale), items));
            data.add(new Map("grade3", R.drawable.map3_3,(int)(1080*scale),(int)(1159*scale), items));
            data.add(new Map("grade3", R.drawable.map3_4,(int)(1080*scale),(int)(1159*scale), items));
            data.add(new Map("grade2", R.drawable.map2_0_1,(int)(1080*scale),(int)(1832*scale), items));
            data.add(new Map("grade2", R.drawable.map2_2,(int)(1080*scale),(int)(916*scale), items));
            data.add(new Map("grade2", R.drawable.map2_3,(int)(1080*scale),(int)(916*scale), items));
            data.add(new Map("grade1", R.drawable.map1_0,(int)(1080*scale),(int)(1021*scale), items));
            data.add(new Map("grade1", R.drawable.map1_1,(int)(1080*scale),(int)(1021*scale), items));
            data.add(new Map("grade1", R.drawable.map1_2,(int)(1080*scale),(int)(1021*scale), items));
            data.add(new Map("grade1", R.drawable.map1_3,(int)(1080*scale),(int)(1021*scale), items));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Prefetch low quality background map images (these are keep in memory)
        for (Map map : data) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; //  1/4 of the original size

            map.placeholder = new BitmapDrawable(BitmapFactory.decodeResource(contextRef.get().getResources(), map.imageId, options));
        }

        return data;
    }

}
