package com.adityakapal362.2d.game.engine.thread;

public class MainThread extends Thread {
			
	public void setFPS(int a) {
		FPS = a;
	}

	private int FPS = 60;
	private final SurfaceHolder surfaceHolder;
	private Sketchware2DGameEngine gamePanel;
	private boolean canRun;

	public MainThread(SurfaceHolder a, Sketchware2DGameEngine b) {
		surfaceHolder = a;
		gamePanel = b;
	}

	@Override
	public void run() {
		super.run();
		Canvas canvas = null;
		while (canRun) {
			try {
				canvas = surfaceHolder.lockHardwareCanvas();
				if (gamePanel.firstRen) {
					synchronized (surfaceHolder) {
					    gamePanel.draw(canvas);
					}
				}
			} catch (Exception e) {
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
					gamePanel.fps++;
				}
			}
			try{sleep(10);}catch(Exception e){}
		}
	}

	public void setRunning(boolean b) {
	    canRun = b;
	}

}