package com.docomodigital.test.verticalrecycleview.MapView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docomodigital.test.verticalrecycleview.R;
import com.docomodigital.test.verticalrecycleview.MapView.model.Sticker;

import java.util.Collections;
import java.util.List;

/**
 * Created by tobia.caneschi on 20/04/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    List<MapLevel> list = Collections.emptyList();
    Context context;
    private boolean isVector;

    public RecyclerViewAdapter(List<MapLevel> list, Context context, boolean isVectorValue) {
        this.list = list;
        this.context = context;
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
                    Bitmap bitmap = null;
                    Bitmap scaledBitmap = null;
                    try {
                        int width = getFileWidth(position);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        options.outWidth = list.get(position).imageWidth;
                        options.outHeight = list.get(position).imageHeight;
                        bitmap = BitmapFactory.decodeResource(context.getResources(), list.get(position).imageId, options);
                        if (isCancelled()) return null;
                        if (width<=list.get(position).imageWidth) return bitmap;
                        scaledBitmap = Bitmap.createScaledBitmap(bitmap,list.get(position).imageWidth, list.get(position).imageHeight, false );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return scaledBitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (!isCancelled())
                        if (bitmap!=null){
                            holder.imageView.setImageBitmap(bitmap);

                        }
                }
            };

            holder.imageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (holder.async.getStatus() != AsyncTask.Status.RUNNING && holder.async.getStatus() != AsyncTask.Status.FINISHED)
                        holder.async.execute();
                }
            },10);

                //holder.imageView.setImageResource(list.get(position).imageId);


        } else {
            holder.imageView.setImageResource(list.get(position).imageId);
        }

        if (list.get(position).stickersList != null)
        for (Sticker sticker : list.get(position).stickersList) {
            StickersView stickerview = new StickersView(context);
            holder.frameLayout.addView(stickerview);
            stickerview.setSticker(sticker);
            stickerview.startDalyedAnimation();
        }
    }

    private int getFileWidth(int position) {
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), list.get(position).imageId, dimensions);
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

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, MapLevel data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified MapLevel object
    public void remove(MapLevel data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
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