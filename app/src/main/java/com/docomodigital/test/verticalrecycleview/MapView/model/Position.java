package com.docomodigital.test.verticalrecycleview.MapView.model;

/**
 * Created by matteo on 29/01/15.
 */
public class Position {
    int height;
    int width;
    int x;
    int y;

    public Position() {};

    public Position(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}