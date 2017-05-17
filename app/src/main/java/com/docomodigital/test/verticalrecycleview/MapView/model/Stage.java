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
        root_animation_dir = root_animation_dir + "animation";


        try {
            //LEVEL 3
            items.add(new Item("montgolfiere", new Position((int) (648 *scale), (int) (0*scale), (int) (547*scale), (int) (793*scale)), root_animation_dir, 40));
            items.add(new Item("nuages", new Position((int) (180 *scale), (int) (630*scale), (int) (480*scale), (int) (250*scale)), root_animation_dir, 40));
            data.add(new Map("grade3", R.drawable.map3_0,(int)(1080*scale),(int)(1159*scale), items));

            items = new ArrayList<>();
            data.add(new Map("grade3", R.drawable.map3_1,(int)(1080*scale),(int)(1159*scale), items));

            items = new ArrayList<>();
            items.add(new Item("avion", new Position((int) (650*scale), (int) (840*scale), (int) (351*scale), (int) (90*scale)), root_animation_dir, 40));
            data.add(new Map("grade3", R.drawable.map3_2,(int)(1080*scale),(int)(1159*scale), items));


            items = new ArrayList<>();
            items.add(new Item("hotel", new Position((int) (50*scale), (int) (240*scale), (int) (424*scale), (int) (426*scale)), root_animation_dir, 200));
            data.add(new Map("grade3", R.drawable.map3_3,(int)(1080*scale),(int)(1159*scale), items));

            items = new ArrayList<>();
            items.add(new Item("bateau", new Position((int) (650*scale), (int) (650*scale), (int) (274*scale), (int) (314*scale)), root_animation_dir, 80));
            data.add(new Map("grade3", R.drawable.map3_4,(int)(1080*scale),(int)(1159*scale), items));

            //LEVEL 2
            items = new ArrayList<>();
            items.add(new Item("flamand", new Position((int) (150*scale), (int) (10*scale), (int) (522*scale), (int) (1287*scale)), root_animation_dir, 40));
            items.add(new Item("brume2", new Position((int) (-50*scale), (int) (1630*scale), (int) (1737*scale), (int) (162*scale)), root_animation_dir, 40));
            data.add(new Map("grade2", R.drawable.map2_0_1,(int)(1080*scale),(int)(1832*scale), items));

            items = new ArrayList<>();
            items.add(new Item("serpent", new Position((int) (40*scale), (int) (470*scale), (int) (393*scale), (int) (62*scale)), root_animation_dir, 40));
            data.add(new Map("grade2", R.drawable.map2_2,(int)(1080*scale),(int)(916*scale), items));

            items = new ArrayList<>();
            items.add(new Item("brume1", new Position((int) (-50*scale), (int) (470*scale), (int) (1773*scale), (int) (232*scale)), root_animation_dir, 40));
            data.add(new Map("grade2", R.drawable.map2_3,(int)(1080*scale),(int)(916*scale), items));


            //LEVEL 1
            items = new ArrayList<>();
            items.add(new Item("bulles", new Position((int) (920*scale), (int) (270*scale), (int) (98*scale), (int) (303*scale)), root_animation_dir, 40));
            data.add(new Map("grade1", R.drawable.map1_0,(int)(1080*scale),(int)(1021*scale), items));

            items = new ArrayList<>();
            items.add(new Item("vaguebleu", new Position((int) (150*scale), (int) (490*scale), (int) (301*scale), (int) (34*scale)), root_animation_dir, 40));
            items.add(new Item("meduse", new Position((int) (740*scale), (int) (650*scale), (int) (194*scale), (int) (326*scale)), root_animation_dir, 40));
            data.add(new Map("grade1", R.drawable.map1_1,(int)(1080*scale),(int)(1021*scale), items));


            items = new ArrayList<>();
            items.add(new Item("vagueblanche", new Position((int) (350*scale), (int) (200*scale), (int) (301*scale), (int) (34*scale)), root_animation_dir, 40));
            items.add(new Item("baleine", new Position((int) (750*scale), (int) (600*scale), (int) (279*scale), (int) (226*scale)), root_animation_dir, 40));
            data.add(new Map("grade1", R.drawable.map1_2,(int)(1080*scale),(int)(1021*scale), items));

            items = new ArrayList<>();
            items.add(new Item("palmes", new Position((int)(335*scale), (int)(580*scale), (int)(413*scale), (int)(216*scale)), root_animation_dir, 40));
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
