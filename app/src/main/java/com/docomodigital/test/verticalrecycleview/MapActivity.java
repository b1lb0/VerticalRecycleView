package com.docomodigital.test.verticalrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.docomodigital.test.verticalrecycleview.MapView.adapter.RecyclerViewAdapter;
import com.docomodigital.test.verticalrecycleview.MapView.model.Map;
import com.docomodigital.test.verticalrecycleview.MapView.model.Stage;

import java.util.List;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);

        List<Map> data = new Stage(this, "raw", "animation").fill_with_data();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_animation);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplicationContext(), false);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        lm.setStackFromEnd(true);
        lm.setSmoothScrollbarEnabled(true);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }
}
