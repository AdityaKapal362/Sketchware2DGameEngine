package com.adityakapal362.2d.game.engine.object;

public class NPC {
    
    public Paint paint;
	public Bitmap bitmap;
	public String name;
	public int bodyWidth, bodyHeight, nameY, anim, tx, ty;
	public float x, y;

	public NPC(float a, float b, String c, String d, String e) {
		tx = (int) a;
		ty = (int) b;
		x = a * surface.pixell;
		y = b * surface.pixell;
		name = e;
		nameY = (int) getDen(19);
		anim = -1;
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize((int)getDen(8));
		paint.setTextAlign(Paint.Align.CENTER);
		bitmap = surface.spriteBitmaps.get("pl_i_r_1");
		bodyWidth = (int) (bitmap.getWidth() / 2);
		bodyHeight = (int) (bitmap.getHeight() / 2);
	}
				
	public void animate() {
		anim++;
		if (anim == 3) bitmap = surface.spriteBitmaps.get("pl_i_r_2");
		if (anim == 6) bitmap = surface.spriteBitmaps.get("pl_i_r_3");
		if (anim == 9) bitmap = surface.spriteBitmaps.get("pl_i_r_4");
		if (anim == 12) {
			bitmap = surface.spriteBitmaps.get("pl_i_r_1");
			anim = 0;
		}
	}

	public void drawBodyAndName(Canvas a, float cx, float cy) {
		a.drawBitmap(bitmap, x-bodyWidth-cx, y-bodyHeight-cy, null);
	    a.drawText("[NPC] " + name, x-cx, y-cy-nameY, paint);
	}
	
}