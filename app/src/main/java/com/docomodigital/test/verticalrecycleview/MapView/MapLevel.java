package com.docomodigital.test.verticalrecycleview.MapView;

import android.graphics.drawable.Drawable;

import com.docomodigital.test.verticalrecycleview.MapView.model.Sticker;

import java.util.List;

/**
 * Created by tobia.caneschi on 20/04/17.
 */

public class MapLevel {
    public List<Sticker> stickersList;
    public String title;
    public int imageId;
    public int imageHeight;
    public int imageWidth;
    public Drawable placeholder;

    public MapLevel(String title, int imageId, int width, int height, List<Sticker> stickers) {
        this.title = title;
        this.imageId = imageId;
        this.imageWidth = width;
        this.imageHeight = height;
        this.stickersList = stickers;
    }

}