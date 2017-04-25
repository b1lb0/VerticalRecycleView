package com.docomodigital.test.verticalrecycleview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.docomodigital.test.verticalrecycleview.model.Sticker;
import com.docomodigital.test.verticalrecycleview.model.ThumbRect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<MapLevel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);

        List<MapLevel> data = fill_with_data();

        //recyclerView.setHasFixedSize(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_orange);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplication(), false);
        //adapter.setHasStableIds(true);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //lm.setExtraLayoutSpace(getResources().getDisplayMetrics().heightPixels*4);
        //lm.setItemPrefetchEnabled(true);
        //lm.setInitialPrefetchItemCount(10);
        //lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        lm.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    public List<MapLevel> fill_with_data() {

        data = new ArrayList<>();


        float scale = (float) getResources().getDisplayMetrics().widthPixels / (float) 1080 ;
        //if (scale>1) scale = 1;

        //data.add(new MapLevel("grade3", R.drawable.fond_map_grade6,(int)(1080*scale),(int)(5795*scale), null));
        //data.add(new MapLevel("grade3", R.drawable.fond_map_grade5,(int)(1080*scale),(int)(5795*scale), null));
        //data.add(new MapLevel("grade3", R.drawable.fond_map_grade4,(int)(1080*scale),(int)(5795*scale), null));
        //data.add(new MapLevel("grade3", R.drawable.fond_map_grade3,(int)(1080*scale),(int)(5795*scale), null));
        //data.add(new MapLevel("grade2", R.drawable.fond_map_grade2,(int)(1080*scale),(int)(3365*scale), null));

        data.add(new MapLevel("grade2", R.drawable.map3_0,(int)(1080*scale),(int)(1159*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map3_1,(int)(1080*scale),(int)(1159*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map3_2,(int)(1080*scale),(int)(1159*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map3_3,(int)(1080*scale),(int)(1159*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map3_4,(int)(1080*scale),(int)(1159*scale), null));

        data.add(new MapLevel("grade2", R.drawable.map2_0,(int)(1080*scale),(int)(916*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map2_1,(int)(1080*scale),(int)(916*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map2_2,(int)(1080*scale),(int)(916*scale), null));
        data.add(new MapLevel("grade2", R.drawable.map2_3,(int)(1080*scale),(int)(916*scale), null));

        try {
            List<Sticker> stickers = new ArrayList<>();
            stickers.add(new Sticker("baleine", new ThumbRect((int) (750*scale), (int) (-100*scale), (int) (279*scale), (int) (226*scale)), Environment.getExternalStorageDirectory().getPath()));
            stickers.add(new Sticker("palmes", new ThumbRect((int)(335*scale), (int)(0*scale), (int)(413*scale), (int)(216*scale)), Environment.getExternalStorageDirectory().getPath()));

            data.add(new MapLevel("grade1", R.drawable.map1_0,(int)(1080*scale),(int)(1021*scale), null));
            data.add(new MapLevel("grade1", R.drawable.map1_1,(int)(1080*scale),(int)(1021*scale), null));
            data.add(new MapLevel("grade1", R.drawable.map1_2,(int)(1080*scale),(int)(1021*scale), null));
            data.add(new MapLevel("grade1", R.drawable.map1_3,(int)(1080*scale),(int)(1021*scale), stickers));
        } catch (Exception e) {
            e.printStackTrace();
        }


        int counter = 0;
        for (MapLevel map : data) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            map.placeholder = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), map.imageId, options));
        }
        //}

        return data;
    }

    private int countPlaceHolders() {
        int counter = 0;
        for (MapLevel map : data) {
            if (map.placeholder!= null) counter++;
        }
        return counter;
    }


    public class PreCachingLayoutManager extends LinearLayoutManager {
        private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 600;
        private int extraLayoutSpace = -1;
        private Context context;

        public PreCachingLayoutManager(Context context) {
            super(context);
            this.context = context;
        }

        public PreCachingLayoutManager(Context context, int extraLayoutSpace) {
            super(context);
            this.context = context;
            this.extraLayoutSpace = extraLayoutSpace;
        }

        public PreCachingLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
            this.context = context;
        }

        public void setExtraLayoutSpace(int extraLayoutSpace) {
            this.extraLayoutSpace = extraLayoutSpace;
        }

        @Override
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            if (extraLayoutSpace > 0) {
                return extraLayoutSpace;
            }
            return DEFAULT_EXTRA_LAYOUT_SPACE;
        }
    }
}
