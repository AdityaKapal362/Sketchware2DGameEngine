package com.adityakapal362.s2dge.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class NPC extends Sprite {

        public Paint paint;
		public Bitmap bitmap;
		public String name, npcid;
		public int bodyWidth, bodyHeight, nameY, anim, tx, ty, x, y;
		
		public NPC(int a, int b, String c, String d, String e) {
			tx = a;
			ty = b;
			name = e;
			nameY = 38;
			anim = -1;
			paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
			paint.setTextSize(18);
			paint.setTextAlign(Paint.Align.CENTER);
			npcid = c;
		}
		
		public void drawBodyAndName(Canvas a, float cx, float cy) {
			a.drawBitmap(bitmap, x-bodyWidth-cx, y-bodyHeight-cy, null);
			a.drawText("[NPC] " + name, x-cx, y-cy-nameY, paint);
		}

}
