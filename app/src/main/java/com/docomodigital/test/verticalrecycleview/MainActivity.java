package com.docomodigital.test.verticalrecycleview;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.docomodigital.test.verticalrecycleview.MapView.MapLevel;
import com.docomodigital.test.verticalrecycleview.MapView.RecyclerViewAdapter;
import com.docomodigital.test.verticalrecycleview.MapView.model.Sticker;
import com.docomodigital.test.verticalrecycleview.MapView.model.ThumbRect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<MapLevel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);

        List<MapLevel> data = fill_with_data();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_orange);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplication(), false);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        lm.setStackFromEnd(true);
        lm.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }


    public List<MapLevel> fill_with_data() {

        data = new ArrayList<>();


        float scale = (float) getResources().getDisplayMetrics().widthPixels / (float) 1080 ;


        List<Sticker> stickers = new ArrayList<>();

        long starttime = System.currentTimeMillis() / 1000;
        try {
            //LEVEL 3
            stickers.add(new Sticker("montgolfiere", new ThumbRect((int) (648 *scale), (int) (0*scale), (int) (547*scale), (int) (793*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            stickers.add(new Sticker("nuages", new ThumbRect((int) (180 *scale), (int) (630*scale), (int) (480*scale), (int) (250*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade3", R.drawable.map3_0,(int)(1080*scale),(int)(1159*scale), stickers));

            stickers = new ArrayList<>();
            data.add(new MapLevel("grade3", R.drawable.map3_1,(int)(1080*scale),(int)(1159*scale), stickers));

            stickers = new ArrayList<>();
            stickers.add(new Sticker("avion", new ThumbRect((int) (650*scale), (int) (840*scale), (int) (351*scale), (int) (90*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade3", R.drawable.map3_2,(int)(1080*scale),(int)(1159*scale), stickers));


            stickers = new ArrayList<>();
            stickers.add(new Sticker("hotel", new ThumbRect((int) (50*scale), (int) (240*scale), (int) (424*scale), (int) (426*scale)), Environment.getExternalStorageDirectory().getPath(), 200));
            data.add(new MapLevel("grade3", R.drawable.map3_3,(int)(1080*scale),(int)(1159*scale), stickers));

            stickers = new ArrayList<>();
            stickers.add(new Sticker("bateau", new ThumbRect((int) (650*scale), (int) (650*scale), (int) (274*scale), (int) (314*scale)), Environment.getExternalStorageDirectory().getPath(), 80));
            data.add(new MapLevel("grade3", R.drawable.map3_4,(int)(1080*scale),(int)(1159*scale), stickers));

            //LEVEL 2
            stickers = new ArrayList<>();
            stickers.add(new Sticker("flamand", new ThumbRect((int) (150*scale), (int) (10*scale), (int) (522*scale), (int) (1287*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            stickers.add(new Sticker("brume2", new ThumbRect((int) (-50*scale), (int) (1630*scale), (int) (1737*scale), (int) (162*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade2", R.drawable.map2_0_1,(int)(1080*scale),(int)(1832*scale), stickers));

            stickers = new ArrayList<>();
            stickers.add(new Sticker("serpent", new ThumbRect((int) (40*scale), (int) (470*scale), (int) (393*scale), (int) (62*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade2", R.drawable.map2_2,(int)(1080*scale),(int)(916*scale), stickers));

            stickers = new ArrayList<>();
            stickers.add(new Sticker("brume1", new ThumbRect((int) (-50*scale), (int) (470*scale), (int) (1773*scale), (int) (232*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade2", R.drawable.map2_3,(int)(1080*scale),(int)(916*scale), stickers));


            //LEVEL 1
            stickers = new ArrayList<>();
            stickers.add(new Sticker("bulles", new ThumbRect((int) (920*scale), (int) (270*scale), (int) (98*scale), (int) (303*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade1", R.drawable.map1_0,(int)(1080*scale),(int)(1021*scale), stickers));

            stickers = new ArrayList<>();
            stickers.add(new Sticker("vaguebleu", new ThumbRect((int) (150*scale), (int) (490*scale), (int) (301*scale), (int) (34*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            stickers.add(new Sticker("meduse", new ThumbRect((int) (740*scale), (int) (650*scale), (int) (194*scale), (int) (326*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade1", R.drawable.map1_1,(int)(1080*scale),(int)(1021*scale), stickers));


            stickers = new ArrayList<>();
            stickers.add(new Sticker("vagueblanche", new ThumbRect((int) (350*scale), (int) (200*scale), (int) (301*scale), (int) (34*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            stickers.add(new Sticker("baleine", new ThumbRect((int) (750*scale), (int) (600*scale), (int) (279*scale), (int) (226*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade1", R.drawable.map1_2,(int)(1080*scale),(int)(1021*scale), stickers));

            stickers = new ArrayList<>();
            stickers.add(new Sticker("palmes", new ThumbRect((int)(335*scale), (int)(580*scale), (int)(413*scale), (int)(216*scale)), Environment.getExternalStorageDirectory().getPath(), 40));
            data.add(new MapLevel("grade1", R.drawable.map1_3,(int)(1080*scale),(int)(1021*scale), stickers));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("TIME animation:" , " "+((int)(System.currentTimeMillis()/1000) - starttime) +" secs");
        starttime = System.currentTimeMillis() / 1000;

        //Prefetch low quality background map images (these are keep in memory)
        for (MapLevel map : data) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; //  1/4 of the original size

            map.placeholder = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), map.imageId, options));
        }

        /*for (int i=data.size()-3; i<data.size(); i++) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; //  1/4 of the original size
            data.get(i).placeholder = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), data.get(i).imageId, options));
        }*/

        Log.d("TIME preload maps:" , " "+((int)(System.currentTimeMillis()/1000) - starttime) +" secs");
        return data;
    }

}
