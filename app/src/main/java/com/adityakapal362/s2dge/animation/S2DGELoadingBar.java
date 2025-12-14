package com.adityakapal362.s2dge.animation;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.adityakapal362.s2dge.Sketchware2DGameEngine;

public class S2DGELoadingBar {
		
		private Paint loadBgPaint = new Paint();
		private Paint loadFgPaint = new Paint();
		private float loadProgress = 0f;
		private float loadAnimProgress = 0f;
		private float easeSpeed = 0.12f;
		private Paint shimmerPaint = new Paint();
		private LinearGradient shimmerGradient;
		private float shimmerOffset = 0f;
		private Paint glowPaint = new Paint();
		private float glowAlpha = 0.5f;
		private float glowDir = 1f;
		private int barWidth, barHeight, cx, cy, left, top, right, bottom, xmax, ymax;
		private float radius, progRight;
		private RectF bg, glowRect, fg;
		
		public S2DGELoadingBar(Sketchware2DGameEngine a) {
			loadBgPaint.setColor(Color.argb(120, 255, 255, 255));
			loadFgPaint.setColor(Color.WHITE);
			loadBgPaint.setAntiAlias(true);
			loadFgPaint.setAntiAlias(true);
			shimmerPaint.setAntiAlias(true);
			shimmerPaint.setStyle(Paint.Style.FILL);
			glowPaint.setColor(Color.WHITE);
			glowPaint.setAntiAlias(true);
			glowPaint.setMaskFilter(new BlurMaskFilter(12, BlurMaskFilter.Blur.NORMAL));
			barWidth = (int)(a.screenXMax * 0.3f);
			barHeight = 20;
			xmax = a.screenXMax;
			ymax = a.screenYMax;
			cx = a.screenXMax / 2;
			cy = a.screenYMax - 100;
			left = cx - barWidth / 2;
			top = cy - barHeight / 2;
			right = left + barWidth;
			bottom = top + barHeight;
			radius = barHeight / 2f;
			bg = new RectF(left, top, right, bottom);
		}
		
		public void animate() {
			loadAnimProgress += (loadProgress - loadAnimProgress) * easeSpeed;
			effects();
		}
		
		public void effects() {
			shimmerOffset += 8;
			if (shimmerOffset > xmax) shimmerOffset = -xmax;
			glowAlpha += glowDir * 0.02f;
			if (glowAlpha > 1f) { glowAlpha = 1f; glowDir = -1f; }
			if (glowAlpha < 0.3f) { glowAlpha = 0.3f; glowDir = 1f; }
		}
		
		public void draw(Canvas a) {
			glowPaint.setAlpha((int)(glowAlpha * 180));
			glowRect = new RectF(left - 6, top - 6, right + 6, bottom + 6);
			a.drawRoundRect(glowRect, radius + 4, radius + 4, glowPaint);
			a.drawRoundRect(bg, radius, radius, loadBgPaint);
			progRight = left + (barWidth * loadAnimProgress);
			fg = new RectF(left, top, progRight, bottom);
			a.drawRoundRect(fg, radius, radius, loadFgPaint);
			shimmerGradient = new LinearGradient(
			shimmerOffset, 0,
			shimmerOffset + 80, 0,
			new int[]{
				Color.argb(0, 255, 100, 100),
				Color.argb(150, 255, 100, 100),
				Color.argb(0, 255, 100, 100)
			},
			new float[]{0f, 0.5f, 1f},
			Shader.TileMode.CLAMP
			);
			shimmerPaint.setShader(shimmerGradient);
			a.save();
			a.clipRect(fg);
			a.drawRoundRect(fg, radius, radius, shimmerPaint);
			a.restore();
		}
		
		public float clamp(float v, float min, float max) {
			return Math.max(min, Math.min(max, v));
		}
		
		public void req() {
			loadProgress = clamp(loadProgress+0.005f, 0f, 1f);
		}
		
	}