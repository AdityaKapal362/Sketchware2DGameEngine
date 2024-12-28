package com.adityakapal362.2d.game.engine.item;

import com.adityakapal362.2d.game.engine.Sprite;

public class ItemSpawned extends Sprite {

	public String itemName;
	public Paint paint;
	public int value;

	public ItemSpawned() {
	}

	public ItemSpawned(String a, int b) {
		itemName = a;
		value = b;
		set(Bitmap.createScaledBitmap(surface.spriteBitmaps.get("bag1"), (int)getDen(20), (int)getDen(20), false));
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(getDen(8));
		paint.setTextAlign(Paint.Align.CENTER);
	}

	public void draw(Canvas a, float b, float c) {
		a.drawBitmap(this.bitmap, this.x-this.halfWidth-surface.camX, this.y-this.halfHeight-surface.camY, null);
		a.drawText(itemName, this.x-surface.camX+this.width, this.y-surface.camY+getDen(3), paint);
	}

}