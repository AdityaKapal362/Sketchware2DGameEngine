package com.adityakapal362.s2dge.util;

public class Player extends Sprite {
    public int x, y, bodyWidth, bodyHeight;
    public String playerName, playerId;
    public Bitmap playerBitmap;

    public Player(int a, int b, String c, String g, Bitmap d, int e, int f) {
        x = a;
        y = b;
        playerName = c;
        playerBitmap = d;
        playerId = g;
        bodyWidth = e;
        bodyHeight = f;
        set(d, 0);
    }

    public void update() {
        
    }
}