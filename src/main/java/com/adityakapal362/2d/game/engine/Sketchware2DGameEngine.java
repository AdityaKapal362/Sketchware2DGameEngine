package com.adityakapal362.2d.game.engine;

public class Sketchware2DGameEngine extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
			
	/*
	
	SKETCHWARE 2D GAME ENGINE LIBRARY
	
	FULLY CREATED BY "ADITYAKAPAL362"
	on ~ 5 August 2020
	
	#1 ~ Updated on 23 January 2022
	=== [Engine version 0.3, map version 1b]
	
	#2 ~ Updated on 12 December 2023
	=== [Engine version 0.4, map version 1b]
	
	#3 ~ Updated on 14 October 2024
	=== [Engine version 0.5, map version 1c]
	
	##### MUST READ #####
	
	[NOT ALLOWED]:
	- DO NOT REMOVE/MODIFY THIS LICENSE.
	- DO NOT SELL THIS LIBRARY (LOL)
	
	[ALLOWED]
	- YOU ARE ALLOWED TO IMPROVE/MODIFY/USE THIS LIBRARY WITH KEEPING THE LICENSE.
	- YOU ARE ALLOWED TO SHARE THIS PROJECT TO YOUR GROUPS/COMMUNITIES/YOUTUBE CHANNELS.
	
	===
	IF I FIND SOMEONE DO THINGS WHICH NOT ALLOWED, I WILL STOP UPDATING MY PROJECTS FOREVER.
	
	*/
			
	private int mGameState, startX, startY, endX, endY, mapSizeX, mapSizeY, camTileX, camTileY, screenXMax, screenYMax, screenXOffset, screenYOffset, fps, frameCounter = 0;
	private int STATE_RUNNING = 1;
	private int STATE_PAUSED = 2;
	private long currentTime, lastFpsTime = 0;
	private float mScreenDensity, touchX, touchY, camX, camY, pixel, pixell;
	private Paint tempPaint, fpsPaint = new Paint();
	private boolean mGameRun, showPlayersName = true;
	private boolean showFps, firstRen, shadow = false;
	private Point spawn;
	private SurfaceHolder holder;
	private OnGenerateListener listener;
	private Canvas cnvs;
	private MainThread thread;
	private Tiles[][] tile;
	private String gamePath, currentMap, currentMapName;
	private HashMap<String, Bitmap> tileBitmaps = new HashMap<>();
	private HashMap<String, Bitmap> spriteBitmaps = new HashMap<>();
	private HashMap<String, String> sfx = new HashMap<>();
	private ArrayList<NPC> npcs = new ArrayList<>();
	private ArrayList<Sprite> spritesData = new ArrayList<>();
	private ArrayList<ItemSpawned> spawnedItemsData = new ArrayList<>();

	public Sketchware2DGameEngine(Context a) {
		super(a);
		//setLayerType(View.LAYER_TYPE_HARDWARE, null);
		pixel = 44;
		pixell = getDen(pixel);
		setOnTouchListener(this);
		fpsPaint = new Paint();
		fpsPaint.setStyle(Paint.Style.FILL);
		fpsPaint.setColor(Color.WHITE);
		fpsPaint.setAntiAlias(true);
		fpsPaint.setTextSize((int)getDen(11));
		screenXMax = (int) getResources().getDisplayMetrics().widthPixels;
		screenYMax = (int) getResources().getDisplayMetrics().heightPixels;
		screenXOffset = (int) (screenXMax/2);
		screenYOffset = (int) (screenYMax/2);
		getHolder().addCallback(this);
	}

	public void surfaceChanged(android.view.SurfaceHolder a, int b, int c, int d) {
		screenXMax = c;
		screenYMax = d;
		screenXOffset = (int) (screenXMax/2);
		screenYOffset = (int) (screenYMax/2);
    }

	public void surfaceCreated(android.view.SurfaceHolder a) {
		screenXMax = getWidth();
		screenYMax = getHeight();
		screenXOffset = (int) (screenXMax/2);
		screenYOffset = (int) (screenYMax/2);
		thread = new MainThread(a, this);
		if (thread.getState() == Thread.State.NEW) {
			thread.setRunning(true);
			thread.start();
		}
	}

	public void surfaceDestroyed(android.view.SurfaceHolder a) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
				thread.setRunning(true);
			} catch (Exception e) {
			}
		}
	}

	public void setGameFilePath(String a) {
		gamePath = a;
	}

	public void setGenerateListener(OnGenerateListener a) {
		listener = a;
	}
			
	public void startEngine() {
		listener.onGenerateStart(0);
		new GenerateTask().execute(this);
	}

	public void drawTiles(Canvas a) {
		startX = Math.max(0, (int) (camX / pixell));
		startY = Math.max(0, (int) (camY / pixell));
		endX = Math.min(mapSizeX, (int) ((camX + screenXMax) / pixell + 1));
		endY = Math.min(mapSizeY, (int) ((camY + screenYMax) / pixell + 1));
		int kfx = startX;
		while (startX < endX && startY < endY) {
			drawTile(a,tile[startX][startY]);
			startX++;
			if (startX==endX) {
				startX = kfx;
				startY++;
			}
		}
    }

	public void drawTile(Canvas a, Tiles b) {
		if (b.tilesId.equals("1")) {
			a.drawBitmap(tileBitmaps.get(wkwk), null, new RectF(b.x-camX, b.y-camY, b.x-camX+pixell, b.y-camY+pixell), null);
		} else {
			a.drawBitmap(b.bitmap, null, new RectF(b.x-camX, b.y-camY, b.x-camX+pixell, b.y-camY+pixell), null);
		}
		if (!b.mask.equals("0")) a.drawBitmap(b.maskBitmap, null, new RectF(b.x-camX, b.y-camY, b.x-camX+pixell, b.y-camY+pixell), null);
	}

	public void drawTile(Canvas a, int b, int c, float d, float e) {
		if (tile[b][c].tilesId.equals("1")) {
			a.drawBitmap(tileBitmaps.get(wkwk), null, new RectF(d, e, d+pixell, e+pixell), null);
		} else {
			a.drawBitmap(tile[b][c].bitmap, null, new RectF(d, e, d+pixell, e+pixell), null);
		}
		if (!tile[b][c].mask.equals("0")) a.drawBitmap(tile[b][c].maskBitmap, null, new RectF(d, e, d+pixell, e+pixell), null);
	}

	public void drawSpawnedItems(Canvas a) {
		for (int k = 0; k < spawnedItemsData.size(); k++) {
			spawnedItemsData.get(k).draw(a, camX, camY);
		}
	}

	public void drawSprites(Canvas a) {
        for (Sprite r : spritesData) {
			r.draw(a, camX, camY);		
		}
    }

	public void drawPlayers(Canvas a) {
		a.drawBitmap(me.playerBitmap, me.x - camX - me.bodyWidth, me.y - camY - me.bodyHeight, null);
	}

	public void drawNPCs(Canvas a) {
		if (npcs == null) return;
		for (NPC npc : npcs) {
			npc.drawBodyAndName(a, camX, camY);
		}
	}

    public void draw(Canvas a) {
		a.drawColor(Color.TRANSPARENT);
		drawTiles(a);
		drawPlayers(a);
		drawNPCs(a);
		a.drawText(me.playerName, screenXOffset, screenYOffset - 60, me.paint);
		if (showFps) a.drawText(String.valueOf((int)fps) + " fps", 40, 40, fpsPaint);
		a.drawText(String.valueOf(endX-startX) + " - " + String.valueOf(endY-startY), 80, 110, fpsPaint);
	}
			
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (firstRen == true) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					touchX = event.getX();
					touchY = event.getY();
					updatePlayerPosition(event.getX(), event.getY());
				return true;
				case MotionEvent.ACTION_MOVE:
				    updatePlayerPosition(event.getX(), event.getY());
				return true;
            }
		}
		return super.onTouchEvent(event);
	}

	private void updatePlayerPosition(float x, float y) {
		try {
			Thread.sleep(10);
			if (x < touchX) { me.x -= 3; me.angle = "l"; };
			if (x > touchX) { me.x += 3; me.angle = "r"; };
			if (y < touchY) me.y -= 3;
			if (y > touchY) me.y += 3;
			me.y-=3;
			camX = me.x - screenXOffset;
			camY = me.y - screenYOffset;
			touchX = x;
			touchY = y;
		} catch (Exception e) {
		}
	}

	public void setShowFPS(boolean a) {
		showFps = a;
	}

	public void setShowPlayersName(boolean a) {
		showPlayersName = a;
	}

	public void pause() {
		synchronized (holder) {
			mGameState = STATE_PAUSED;
		}
	}

	public void unpause() {
		synchronized (holder) {
			mGameState = STATE_RUNNING;
		}
	}
    
}