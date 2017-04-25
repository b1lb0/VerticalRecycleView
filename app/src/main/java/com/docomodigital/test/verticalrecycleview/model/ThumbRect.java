package com.docomodigital.test.verticalrecycleview.model;

/**
 * Created by matteo on 29/01/15.
 */
public class ThumbRect {
    int height;
    int width;
    int x;
    int y;

    public ThumbRect() {};

    public ThumbRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isValid () {
        return (x>=0) && (y>=0) && (width > 0) && (height > 0);
    }
}