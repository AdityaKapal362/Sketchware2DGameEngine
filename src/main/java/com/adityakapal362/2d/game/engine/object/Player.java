package com.adityakapal362.2d.game.engine.object;

public class Player {
			
	public Paint paint;
	public Bitmap playerBitmap;
	public String playerName, playerUid, state, angle;
	public int tier, bodyWidth, bodyHeight, nameY, anim, x, y;
	public double speed;
			
	public Player(String a, String b, int c) {
		x = 0;
		y = 0;
		playerName = a;
		playerUid = b;
		angle = "r";
		state = "i";
		tier = c;
		speed = 1.8 + (0.3*c);
		nameY = (int) getDen(16);
		anim = -1;
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(tier == 0 ? Color.WHITE : tier == 1 ? Color.GREEN : tier == 2 ? Color.BLUE : tier == 3 ? Color.RED : Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize((int)getDen(9));
		paint.setTextAlign(Paint.Align.CENTER);
		playerBitmap = surface.spriteBitmaps.get("pl_i_r_1");
		bodyWidth = (int) (playerBitmap.getWidth() / 2);
		bodyHeight = (int) (playerBitmap.getHeight() / 2);
	}
			
	public void animate() {
		anim++;
		if (anim == 3) playerBitmap = surface.spriteBitmaps.get("pl_" + state + "_" + angle + "_2");
		if (anim == 6) playerBitmap = surface.spriteBitmaps.get("pl_" + state + "_" + angle + "_3");
		if (anim == 9) playerBitmap = surface.spriteBitmaps.get("pl_" + state + "_" + angle + "_4");
		if (anim == 12) {
			playerBitmap = surface.spriteBitmaps.get("pl_" + state + "_" + angle + "_1");
			anim = 0;
		}
	}
			
	public void drawBodyAndName(Canvas a, int cx, int cy) {
		a.drawBitmap(playerBitmap, x-bodyWidth-cx, y-bodyHeight-cy, null);
	}
			
}