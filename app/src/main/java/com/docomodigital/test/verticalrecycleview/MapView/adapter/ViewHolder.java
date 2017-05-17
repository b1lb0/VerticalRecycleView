package com.docomodigital.test.verticalrecycleview.MapView.adapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.docomodigital.test.verticalrecycleview.R;

/**
 * Created by tobia.caneschi on 20/04/17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout frameLayout;
    ImageView imageView;
    public AsyncTask<Object, Object, Bitmap> async;

    public ViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        frameLayout= (RelativeLayout) itemView.findViewById(R.id.frameLayout);
    }
}