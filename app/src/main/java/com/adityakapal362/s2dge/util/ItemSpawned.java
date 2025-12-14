package com.adityakapal362.s2dge.util;

import android.graphics.Bitmap;

public class ItemSpawned extends Sprite {

    public int x, y;
    public String id, name, hook;
    public Bitmap maskBitmap;

    public ItemSpawned(int a, int b, String c, String d, Bitmap e) {
        x = a;
        y = b;
        id = c;
        name = d;
        maskBitmap = e;
        set(e, 0);
    }

}
