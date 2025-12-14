package com.adityakapal362.s2dge.util;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player extends Sprite {
    public int x, y, bodyWidth, bodyHeight;
    public String playerName, playerId, angle;
    public Bitmap playerBitmap;
    public Paint paint;
    public Rect ghostHitbox;

    public Player(int a, int b, String c, String g, Bitmap d, int e, int f) {
        x = a;
        y = b;
        playerName = c;
        playerBitmap = d;
        playerId = g;
        bodyWidth = e;
        bodyHeight = f;
        paint = new Paint();
        ghostHitbox = new Rect();
        set(d, 0);
    }

    public void updateGhostBox(int a, int b) {
        x = a;
        y = b;
    }

    public void update() {

    }
}