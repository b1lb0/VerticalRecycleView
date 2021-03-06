package com.docomodigital.test.verticalrecycleview.MapView.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docomodigital.test.verticalrecycleview.MapView.view.AnimationView;
import com.docomodigital.test.verticalrecycleview.MapView.model.Item;
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
        holder.frameLayout.removeAllViews();

        if (!isVector) {
            holder.async = new AsyncTask<Object, Object, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Object... params) {
                    Bitmap bitmap;
                    Bitmap scaledBitmap = null;
                    try {
                        int width = getFileWidth(position);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        options.outWidth = list.get(position).imageWidth;
                        options.outHeight = list.get(position).imageHeight;
                        bitmap = BitmapFactory.decodeResource(contextRef.get().getResources(), list.get(position).imageId, options);
                        if (isCancelled()) return null;
                        if (width <= list.get(position).imageWidth) return bitmap;
                        scaledBitmap = Bitmap.createScaledBitmap(bitmap, list.get(position).imageWidth, list.get(position).imageHeight, false);
                        bitmap.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return scaledBitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (!isCancelled())
                        if (bitmap != null) {
                            holder.imageView.setImageBitmap(bitmap);

                        }
                }
            };

            holder.imageView.postDelayed(() -> {
                if (holder.async.getStatus() != AsyncTask.Status.RUNNING && holder.async.getStatus() != AsyncTask.Status.FINISHED)
                    holder.async.execute();
            }, 1000);

        } else {
            holder.imageView.setImageResource(list.get(position).imageId);
        }

        if (list.get(position).stickersList != null)
            for (Item item : list.get(position).stickersList) {
                AnimationView stickerview = new AnimationView(contextRef.get());
                holder.frameLayout.addView(stickerview);
                stickerview.setItem(item);
                stickerview.startDalyedAnimation();
            }
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