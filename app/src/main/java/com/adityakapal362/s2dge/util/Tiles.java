package com.adityakapal362.s2dge.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tiles extends Sprite {

    public int tilesX, tilesY, tileSize, x, y;
    public String tilesId, tilesType, mask;
    public Bitmap maskBitmap;
    public Rect rect;

    public Tiles(int a, int b, String c, String d, String e, Bitmap f, Bitmap g, int h) {
        tilesX = a;
        tilesY = b;
        tilesId = c;
        tilesType = d;
        mask = e;
        maskBitmap = g;
        tileSize = h;
        set(f, 0);
        x = tilesX * h;
        y = tilesY * h;
        rect = new Rect(x, y, x + h, y + h);
    }

    public void update() {
        rect.set(x, y, x + tileSize, y + tileSize);
    }

    public boolean isSolid() {
        return !"0".equals(tilesType);
    }
}
