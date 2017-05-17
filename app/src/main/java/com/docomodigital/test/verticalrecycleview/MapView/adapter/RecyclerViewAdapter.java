package com.docomodigital.test.verticalrecycleview.MapView.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docomodigital.test.verticalrecycleview.MapView.model.Map;
import com.docomodigital.test.verticalrecycleview.R;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * Created by tobia.caneschi on 20/04/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private WeakReference<Context> contextRef;
    List<Map> list = Collections.emptyList();
    private boolean isVector;

    public RecyclerViewAdapter(List<Map> list, Context context, boolean isVectorValue) {
        this.list = list;
        this.contextRef = new WeakReference<>(context);
        this.isVector = isVectorValue;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView

        ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
        lp.width = list.get(position).imageWidth;
        lp.height = list.get(position).imageHeight;
        holder.imageView.setLayoutParams(lp);
        holder.imageView.setImageDrawable(list.get(position).placeholder);

        holder.imageView.setImageBitmap(BitmapFactory.decodeResource(contextRef.get().getResources(), list.get(position).imageId, null));
    }

    private int getFileWidth(int position) {
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(contextRef.get().getResources(), list.get(position).imageId, dimensions);
        return dimensions.outWidth;
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        if (holder.async != null) holder.async.cancel(true);
        holder.imageView.setImageDrawable(null);
    }
}