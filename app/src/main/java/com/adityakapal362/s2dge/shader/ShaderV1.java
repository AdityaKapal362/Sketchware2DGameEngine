package com.adityakapal362.s2dge.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.adityakapal362.s2dge.util.Tiles;

public class ShaderV1 {
		
		private Paint paint;
		private int pixell, x, y;
		
		public ShaderV1(int a) {
            pixell = a;
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
			x, y, x+pixell, y+pixell,
			Color.BLACK,
			Color.TRANSPARENT,
			Shader.TileMode.CLAMP
			));
			paint.setAlpha(100);
			a.drawRect(x, y, x+pixell, y+pixell, paint);
		}
		
	}