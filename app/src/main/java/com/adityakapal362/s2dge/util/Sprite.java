package com.adityakapal362.s2dge.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sprite {
		
		public Bitmap bitmap;
		public int width, height, halfWidth, halfHeight, x, y;
		public String hook;
		
		public Sprite() {
		}
		
		public void opt() {
			width = bitmap.getWidth();
			height = bitmap.getHeight();
			halfWidth = (int) (width/2);
			halfHeight = (int) (height/2);
			x = 0;
			y = 0;
		}
		
		public void set(String path) {
				bitmap = BitmapFactory.decodeFile(path);
				opt();
		}
		
		public void set(Bitmap newBitmap) {
			bitmap = Bitmap.createBitmap(newBitmap);
			opt();
		}
		
		public void set(Bitmap newBitmap, int b) {
			bitmap = newBitmap;
			opt();
		}
		
		public void hook(String a) {
			hook = a;
		}
		
		public void draw(Canvas a, int nx, int ny) {
			a.drawBitmap(bitmap, nx, ny, null);
		}
		
		private void clean() {
			if (bitmap != null) { bitmap.recycle(); bitmap = null; };
		}
		
	}