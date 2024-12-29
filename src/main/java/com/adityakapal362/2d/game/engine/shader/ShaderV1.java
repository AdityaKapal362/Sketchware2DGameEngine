package com.adityakapal362.2d.game.engine.shader;

// NOT YET USABLE

public class ShaderV1 {
			
	private Paint paint;
	private int x, y;
			
	public ShaderV1() {
		paint = new Paint();
	}
			
	public boolean isWall(Tiles a) {
		return a.mask.equals("89");
	}
			
	public void renderShaders(Canvas a, Tiles b, int c, int d) {
		if (!isWall(b)) return;
		x = b.x-c;
		y = b.y-d;
		paint.setShader(new LinearGradient(
		    x, y, x+surface.pixell, y+surface.pixell, // Gradient from top-left to bottom-right
		    Color.BLACK, // Start color (black for shadow)
		    Color.TRANSPARENT, // End color (fade out)
		    Shader.TileMode.CLAMP
		));
		paint.setAlpha(100);
		a.drawRect(x, y, x+surface.pixell, y+surface.pixell, paint);
	}
	
}