package com.adityakapal362.2d.game.engine.thread;

public class MainThread extends Thread {
			
	private final SurfaceHolder surfaceHolder;
	private Sketchware2DGameEngine gamePanel;
	private Canvas cvs;
	private boolean canRun;
	private long startTime, frameTime;
	private int frame;
			
	public MainThread(SurfaceHolder a, Sketchware2DGameEngine b) {
		surfaceHolder = a;
		gamePanel = b;
	}
			
	@Override
	public void run() {
		super.run();
		cvs = null;
		frame = 0;
		startTime = System.nanoTime();
		while (canRun) {
			if (gamePanel.firstRen) {
				try {
					cvs = surfaceHolder.lockCanvas();
					synchronized (surfaceHolder) {
						gamePanel.draw(cvs);
					}
				} catch (Exception e) {
				} finally {
					if (cvs != null) {
						surfaceHolder.unlockCanvasAndPost(cvs);
						//gamePanel.fps++;
					}
				}
			}
			frame++;
			frameTime = System.nanoTime();
			if (frameTime-startTime >= 1000000000) {
				frame--;
				gamePanel.fps = frame;
				frame = 0;
				startTime = frameTime;
			}
		}
	}
			
	public void setRunning(boolean b) {
		canRun = b;
	}
	
}