package com.docomodigital.test.verticalrecycleview.MapView.model;

import android.content.Context;
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

        try {
            data.add(new Map("grade3", R.drawable.fond_map_grade3, 1080, 5795, null));
            data.add(new Map("grade2", R.drawable.fond_map_grade2, 1080, 3665, null));
            data.add(new Map("grade1", R.drawable.fond_map_grade1, 1080, 4084, null));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
