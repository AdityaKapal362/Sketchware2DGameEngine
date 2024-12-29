package com.adityakapal362.2d.game.engine;

public class Sketchware2DGameEngine extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
			
	/*
	
	SKETCHWARE 2D GAME ENGINE LIBRARY
	
	FULLY CREATED BY "ADITYAKAPAL362"
	on ~ 5 August 2020
	
	#1 ~ Updated on 23 January 2022 -> Migrated to SurfaceView & Canvas
	=== [Engine version 0.2, map version 1b]
	
	#2 ~ Updated on 12 December 2023 -> Major fix
	=== [Engine version 0.3, map version 1b]
	
	#3 ~ Updated on 14 October 2024 -> Minor update
	=== [Engine version 0.4, map version 1c]
	
	#4 ~ Updated on 29 December 2024 -> Major update
	=== [Engine version 0.5, map version 1d]
	
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
			
	private int mGameState, mapSizeX, mapSizeY, startX, startY, endX, endY, screenXMax, screenYMax, screenXOffset, screenYOffset, fps, touchX, touchY, camX, camY, pixel, pixell = 0;
	private int STATE_RUNNING = 1;
	private int STATE_PAUSED = 2;
	private Paint fpsPaint = new Paint();
	private boolean mGameRun = true;
	private boolean showFps, firstRen, shadow = false;
	private Point spawn;
	private SurfaceHolder holder;
	private OnGenerateListener listener;
	private MainThread thread;
	private Tiles[][] tile;
	private String gamePath, currentMap, currentMapName;
	private HashMap<String, Bitmap> tileBitmaps = new HashMap<>();
	private HashMap<String, Bitmap> spriteBitmaps = new HashMap<>();
	private HashMap<String, String> sfx = new HashMap<>();
	private ArrayList<NPC> npcs = new ArrayList<>();
	private ArrayList<Sprite> spritesData = new ArrayList<>();
	private ArrayList<ItemSpawned> spawnedItemsData = new ArrayList<>();
	private ShaderV1 shader;
			
	public Sketchware2DGameEngine(Context a) {
		super(a);
		shader = new ShaderV1();
		pixel = 44;
		pixell = (int) getDen(pixel);
		setOnTouchListener(this);
		fpsPaint = new Paint();
		fpsPaint.setStyle(Paint.Style.FILL);
		fpsPaint.setColor(Color.WHITE);
		fpsPaint.setAntiAlias(true);
		fpsPaint.setTextSize((int)getDen(14));
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
		startX = Math.max(0, (int) (camX / pixell)-1);
		startY = Math.max(0, (int) (camY / pixell)-1);
		endX = Math.min(mapSizeX, (int) ((camX + screenXMax) / pixell + 2));
		endY = Math.min(mapSizeY, (int) ((camY + screenYMax) / pixell + 2));
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				drawTile(a, tile[x][y]);
			}
		}
	}
			
	public void drawTile(Canvas a, Tiles b) {
		int dX = b.x-camX;
		int dY = b.y-camY;
		/*
		int tX = dX + pixell;
		int tY = dY + pixell;
		Rect dstRect = new Rect(dX, dY, tX, tY);
		Rect dstRect2 = new Rect(dX, dY, tX, tY);
		// TOO HEAVY FOR LOW END DEVICES, BUT THIS IS PRETTY
		// EFFICIENT TO USE SO IM GONNA KEEP IT
		*/
		a.drawBitmap(b.tilesId.equals("1") ? tileBitmaps.get(wkwk) : b.bitmap, dX, dY, null);
		if (!b.mask.equals("0")) a.drawBitmap(b.maskBitmap, dX, dY, null);
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
			
	public void drawNPCsUpper(Canvas a) {
		if (npcs == null) return;
		for (NPC npc : npcs) {
			if (npc.y <= me.y) npc.drawBodyAndName(a, camX, camY);
		}
	}
			
	public void drawNPCsLower(Canvas a) {
		if (npcs == null) return;
		for (NPC npc : npcs) {
			if (npc.y > me.y) npc.drawBodyAndName(a, camX, camY);
		}
	}
			
	public void drawShader(Canvas a) {
		startX = Math.max(0, (int) (camX / pixell)-1);
		startY = Math.max(0, (int) (camY / pixell)-1);
		endX = Math.min(mapSizeX, (int) ((camX + screenXMax) / pixell + 2));
		endY = Math.min(mapSizeY, (int) ((camY + screenYMax) / pixell + 2));
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				shader.renderShaders(a,tile[x][y],camX,camY);
			}
		}
	}
			
	public void draw(Canvas a) {
		drawTiles(a);
		drawNPCsUpper(a);
		drawPlayers(a);
		drawNPCsLower(a);
		drawShader(a);
		a.drawText(me.playerName, screenXOffset, screenYOffset - 60, me.paint);
		a.drawText(String.valueOf((int)fps) + " fps", 30, 40, fpsPaint);
		a.drawText(String.valueOf(endX-startX) + " - " + String.valueOf(endY-startY), 30, 100, fpsPaint);
		a.drawText("x: " + String.valueOf(me.x) + " || y: " + String.valueOf(me.y), 30, 160, fpsPaint);
		a.drawText("[" + String.valueOf(me.x/pixell) + "," + String.valueOf(me.y/pixell) + "]", 30, 220, fpsPaint);
	}
			
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (firstRen == true) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					touchX = (int) event.getX();
					touchY = (int) event.getY();
					updatePlayerPosition((int)event.getX(), (int)event.getY());
					return true;
				case MotionEvent.ACTION_MOVE:
					updatePlayerPosition((int)event.getX(), (int)event.getY());
					return true;
			}
		}
		return super.onTouchEvent(event);
	}
			
	private void updatePlayerPosition(int x, int y) {
		if (x < touchX) { me.x -= 3; me.angle = "l"; };
		if (x > touchX) { me.x += 3; me.angle = "r"; };
		if (y < touchY) me.y -= 3;
		if (y > touchY) me.y += 3;
		camX = me.x - screenXOffset;
		camY = me.y - screenYOffset;
		touchX = x;
		touchY = y;
	}
			
	public void setShowFPS(boolean a) {
		showFps = a;
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