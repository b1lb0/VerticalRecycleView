package com.docomodigital.test.verticalrecycleview.MapView.model;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by tobia.caneschi on 20/04/17.
 */

public class Map {
    public List<Item> stickersList;
    public String title;
    public int imageId;
    public int imageHeight;
    public int imageWidth;
    public Drawable placeholder;

    public Map(String title, int imageId, int width, int height, List<Item> items) {
        this.title = title;
        this.imageId = imageId;
        this.imageWidth = width;
        this.imageHeight = height;
        this.stickersList = items;
    }

}