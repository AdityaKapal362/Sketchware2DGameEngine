package com.adityakapal362.s2dge.thread;

import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.graphics.Color;

import com.adityakapal362.s2dge.Sketchware2DGameEngine;
import com.adityakapal362.s2dge.async.VsyncPacer;

public class MainThread extends Thread {
		
		private final SurfaceHolder surfaceHolder;
		private final Sketchware2DGameEngine gamePanel;
		private boolean canRun = false;
		public final VsyncPacer pacer;
		
		private long startTime;
		private int frameCount;
		
		public MainThread(SurfaceHolder holder, Sketchware2DGameEngine panel, VsyncPacer c) {
			surfaceHolder = holder;
			gamePanel = panel;
			pacer = c;
		}
		
		@Override
		public void run() {
			final int MAX_FPS = 60;
			final long FRAME_TIME = 1000 / MAX_FPS;
			long frameStartTime;
			long frameDuration;
			long sleepTime;
			
			frameCount = 0;
			startTime = System.currentTimeMillis();
			
			while (canRun) {
				frameStartTime = System.currentTimeMillis();
				try {
					pacer.await();
				} catch (InterruptedException e) {
					break;
				}
				Canvas cvs = null;
				
				try {
					cvs = surfaceHolder.lockCanvas();
					if (cvs != null) {
						synchronized (surfaceHolder) {
							cvs.drawColor(Color.BLACK);
							if (gamePanel.reqMX != 0 || gamePanel.reqMY != 0) gamePanel.updatePlayerPosition();
							gamePanel.npc.animateNPCS();
							gamePanel.draw(cvs);
							gamePanel.ldbar.animate();
						}
					}
				} finally {
					if (cvs != null) {
						surfaceHolder.unlockCanvasAndPost(cvs);
					}
				}
				
				frameCount++;
				if (System.currentTimeMillis() - startTime >= 1000) {
					gamePanel.fps = frameCount;
					frameCount = 0;
					startTime = System.currentTimeMillis();
				}
				
				frameDuration = System.currentTimeMillis() - frameStartTime;
				sleepTime = FRAME_TIME - frameDuration;
				if (sleepTime > 0) {
					try {
						sleep(sleepTime);
					} catch (Exception e) {
					}
				}
			}
		}
		
		public void setRunning(boolean running) {
			canRun = running;
		}
	}