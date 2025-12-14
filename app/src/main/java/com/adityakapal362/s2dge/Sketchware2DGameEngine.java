package com.adityakapal362.s2dge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import com.adityakapal362.s2dge.thread.MainThread;
import com.adityakapal362.s2dge.async.PreloadEngine;
import com.adityakapal362.s2dge.async.VsyncPacer;
import com.adityakapal362.s2dge.listener.OnPreloadListener;
import com.adityakapal362.s2dge.listener.PreloadListener;
import com.adityakapal362.s2dge.util.Sprite;
import com.adityakapal362.s2dge.util.Tiles;
import com.adityakapal362.s2dge.util.NPC;
import com.adityakapal362.s2dge.util.NPCS;
import com.adityakapal362.s2dge.util.ItemSpawned;
import com.adityakapal362.s2dge.util.ShaderV1;
import com.adityakapal362.s2dge.util.Player;
import com.adityakapal362.s2dge.animation.S2DGELoadingBar;
import com.adityakapal362.s2dge.animation.AnimatedTiles;

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

#5 ~ Updated on 13 October 2025 -> Major Fixes {black grid glitch bug fixed}
=== [Engine version 0.6, map version 1d]

#6 ~ Updated on 14 December 2025 -> Added Animations & Loading Bar
=== [Engine version 0.7, map version]

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
		public OnPreloadListener listener;
		public MainThread thread;
		public Tiles[][] tile;
		private String gamePath, currentMap, currentMapName;
		private HashMap<String, Bitmap> tileBitmaps = new HashMap<>();
		private HashMap<String, Bitmap> spriteBitmaps = new HashMap<>();
		private HashMap<String, String> sfx = new HashMap<>();
		private ArrayList<NPC> npcs = new ArrayList<>();
		private ArrayList<Sprite> spritesData = new ArrayList<>();
		private ArrayList<ItemSpawned> spawnedItemsData = new ArrayList<>();
		private ShaderV1 shader;
		private VsyncPacer pacer = new VsyncPacer();
		private AnimatedTiles animtiles = new AnimatedTiles();
		public S2DGELoadingBar ldbar;
		public Player me;
		public NPCS npc;
		public int reqMX, reqMY, reqAM = 0;

		public Sketchware2DGameEngine(Context a) {
			super(a);
			//shader = new ShaderV1();
			pixel = 32;
			pixell = (int) getDen(pixel);
			setOnTouchListener(this);
			fpsPaint = new Paint();
			fpsPaint.setStyle(Paint.Style.FILL);
			fpsPaint.setColor(Color.WHITE);
			fpsPaint.setAntiAlias(true);
			fpsPaint.setTextSize((int)getDen(14));
			npc = new NPCS(a, this);
			getHolder().addCallback(this);
		}

		public int getSizeX() {
			return screenXMax;
		}

		public int getSizeY() {
			return screenYMax;
		}

		public int getScreenXOffset() {
			return screenXOffset;
		}

		public int getScreenYOffset() {
			return screenYOffset;
		}

		public Bitmap getBitmap(String a) {
			return spriteBitmaps.get(a);
		}

		public Bitmap getTileBitmap(String a) {
			return tileBitmaps.get(a);
		}

		public int getTileSize() {
			return pixell;
		}

		public String getGamePath() {
			return gamePath;
		}

		public void putSprite(String a, Bitmap b) {
			spriteBitmaps.put(a, b);
		}

		public void putTile(String a, Bitmap b) {
			tileBitmaps.put(a, b);
		}

		public void addSpriteData(Sprite a) {
			spritesData.add(a);
		}

		public void newSpawnPoint() {
			spawn = new Point();
		}

		public void setSpawnX(int a) {
			spawn.x = a;
		}

		public void setSpawnY(int a) {
			spawn.y = a;
		}

		public int getSpawnX() {
			return spawn.x;
		}

		public int getSpawnY() {
			return spawn.y;
		}

		public void setCurrentMapName(String a) {
			currentMapName = a;
		}

		public void setCurrentMapSizeX(int a) {
			mapSizeX = a;
		}

		public void setCurrentMapSizeY(int a) {
			mapSizeY = a;
		}

		public int getMapSizeX() {
			return mapSizeX;
		}

		public int getMapSizeY() {
			return mapSizeY;
		}

		public void setCamX(int a) {
			camX = a;
		}

		public void setCamY(int a) {
			camY = a;
		}

		public void setFirstRender(boolean a) {
			firstRen = a;
		}

		public void setThreadRunning(boolean a) {
			thread.setRunning(a);
		}
		
		public void start() {
			pacer.start();
			thread.start();
		}
		
		public void surfaceChanged(SurfaceHolder a, int b, int c, int d) {
			screenXMax = c;
			screenYMax = d;
			screenXOffset = (int) (screenXMax/2);
			screenYOffset = (int) (screenYMax/2);
		}
		
		public void surfaceCreated(SurfaceHolder a) {
			screenXMax = getWidth();
			screenYMax = getHeight();
			screenXOffset = (int) (screenXMax/2);
			screenYOffset = (int) (screenYMax/2);
			thread = new MainThread(a, this, pacer);
			ldbar = new S2DGELoadingBar(this);
			if (thread.getState() == Thread.State.NEW) {
				thread.setRunning(true);
			}
		}
		
		public void surfaceDestroyed(SurfaceHolder a) {
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
		
		public void setPreloadListener(PreloadListener a) {
			listener = a;
		}
		
		public void startEngine() {
			listener.onLoadStart(0);
			new PreloadEngine().execute(this);
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
			if (b.tilesId.equals("1")) {
				a.drawBitmap(tileBitmaps.get(animtiles.currentFrame), dX, dY, null);
			} else {
				a.drawBitmap(b.bitmap, dX, dY, null);
			}
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
            if (shader == null) return;
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
			if (showFps) {
				a.drawText(me.playerName, screenXOffset, screenYOffset - 60, me.paint);
				a.drawText(String.valueOf((int)fps) + " fps", 30, 40, fpsPaint);
				a.drawText(String.valueOf(endX-startX) + " - " + String.valueOf(endY-startY), 30, 100, fpsPaint);
				a.drawText("x: " + String.valueOf(me.x) + " || y: " + String.valueOf(me.y), 30, 160, fpsPaint);
				a.drawText("[" + String.valueOf(me.x/pixell) + "," + String.valueOf(me.y/pixell) + "]", 30, 220, fpsPaint);
			}
			ldbar.draw(a);
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (firstRen == true) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					touchX = (int) event.getX();
					touchY = (int) event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					reqMX = (int) event.getX();
					reqMY = (int) event.getY();
				}
			}
			return true;
		}
		
		public void updatePlayerPosition() {
			if (reqMX < touchX) { me.x -= 3; me.angle = "l"; };
			if (reqMX > touchX) { me.x += 3; me.angle = "r"; };
			if (reqMY < touchY) me.y -= 3;
			if (reqMY > touchY) me.y += 3;
			camX = me.x - screenXOffset;
			camY = me.y - screenYOffset;
			touchX = reqMX;
			touchY = reqMY;
			reqMX = 0;
			reqMY = 0;
			me.update();
			ldbar.req();
		}
		
		public void checkCollision() {
			if (reqMX < touchX) { me.updateGhostBox(me.x-3,me.y); me.angle = "l"; };
			if (reqMX > touchX) { me.updateGhostBox(me.x+3,me.y); me.angle = "r"; };
			if (reqMY < touchY) me.updateGhostBox(me.x,me.y-3);
			if (reqMY > touchY) me.updateGhostBox(me.x,me.y+3);
			startX = Math.max(0, (int) (camX / pixell)-1);
			startY = Math.max(0, (int) (camY / pixell)-1);
			endX = Math.min(mapSizeX, (int) ((camX + screenXMax) / pixell + 2));
			endY = Math.min(mapSizeY, (int) ((camY + screenYMax) / pixell + 2));
			reqAM = 0;
			for (int y = startY; y < endY; y++) {
				for (int x = startX; x < endX; x++) {
					if (tile[x][y].tilesId.equals("1"))  {
						if (Rect.intersects(tile[x][y].rect,me.ghostHitbox)) reqAM = 1;
					}
				}
			}
			if (reqAM == 0) updatePlayerPosition();
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

		public int getDen(int a) {
		    return (int)(a * 2.5f);
	    }
	
	    public int getDen(float a) {
		    return (int)(a * 2.5f);
	    }
	}