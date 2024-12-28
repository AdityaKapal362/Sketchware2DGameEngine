package com.adityakapal362.2d.game.engine.object;

public class Tiles extends Sprite {

	public int tilesX, tilesY;
	public String tilesId, tilesType,mask;
	public Bitmap maskBitmap;

	public Tiles() {
	}

	public Tiles(int posX, int posY, String c, String d, String e) {
		tilesType = d;
		tilesId = c;
		tilesX = posX;
		tilesY = posY;
		mask = e;
		maskBitmap = e.equals("0") ? null : surface.tileBitmaps.get(e);
		set(surface.tileBitmaps.get(c), 0);
		x = getDen((float)tilesX * surface.pixel);
		y = getDen((float)tilesY * surface.pixel);
	}

}