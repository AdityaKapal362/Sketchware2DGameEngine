package com.adityakapal362.2d.game.engine.object;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tile extends Sprite {
			
	public int tilesX, tilesY;
	public String tilesId, tilesType,mask;
	public Bitmap maskBitmap;
	public Rect rect;
			
	public Tiles(int posX, int posY, String c, String d, String e) {
		tilesType = d;
		tilesId = c;
		tilesX = posX;
		tilesY = posY;
		mask = e;
		maskBitmap = e.equals("0") ? null : surface.tileBitmaps.get(e);
		set(surface.tileBitmaps.get(c), 0);
		x = tilesX * surface.pixell;
		y = tilesY * surface.pixell;
		rect = new Rect(x,y,x+surface.pixell,y+surface.pixell);
	}
			
}