package com.adityakapal362.s2dge.async;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Matrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.adityakapal362.s2dge.Sketchware2DGameEngine;
import com.adityakapal362.s2dge.util.Sprite;
import com.adityakapal362.s2dge.util.Tiles;
import com.adityakapal362.s2dge.util.Player;
import com.adityakapal362.s2dge.util.NPC;
import com.adityakapal362.s2dge.util.NPCS;

public class PreloadEngine extends AsyncTask <Sketchware2DGameEngine, Void, Sketchware2DGameEngine> {
		public BufferedReader f;
		public int h = 0;
		public String error = "-";
		public String[] rwog;
		
		public String getFileName(String e) {
			return e.substring(e.lastIndexOf("/")+1, e.lastIndexOf("."));
		}
		
		@Override
		public Sketchware2DGameEngine doInBackground(Sketchware2DGameEngine... i) {
			try {
				File pafr = new File(i[0].getGamePath() + "/gfx");
				if (pafr.exists()) {
					File[] lf1 = pafr.listFiles();
					for (File fl : lf1) {
						if (fl.isFile()) {
							if (getFileName(fl.getAbsolutePath()).length() > 2 && getFileName(fl.getAbsolutePath()).substring(0,3).equals("pl_")) {
								Bitmap kqpd = BitmapFactory.decodeFile(fl.getAbsolutePath());
								i[0].putSprite(getFileName(fl.getAbsolutePath()), Bitmap.createScaledBitmap(kqpd, (int)getDen(kqpd.getWidth()*2f), (int)getDen(kqpd.getHeight()*2f), false));
								kqpd = null;
							} else {
								i[0].putSprite(getFileName(fl.getAbsolutePath()), BitmapFactory.decodeFile(fl.getAbsolutePath()));
							}
						}
					};
					pafr = new File(i[0].getGamePath() + "/sfx");
					if (pafr.exists() && pafr.isDirectory()) {
						File[] lf2 = pafr.listFiles();
						for (File fl : lf2) {
							if (fl.isFile()) {
								//sfx nya dittt
							}
						};
						pafr = new File(i[0].getGamePath() + "/maps");
						if (pafr.exists() && pafr.isDirectory()) {
							File pafr2 = new File(i[0].getGamePath() + "/maps/main.s2dge");
							if (pafr2.exists() && pafr2.isFile()) {
								pafr = new File(i[0].getGamePath() + "/tiles");
								if (pafr.exists() && pafr.isDirectory()) {
									File[] lf3 = pafr.listFiles();
									for (File fl : lf3) {
										if (fl.isFile()) {
											i[0].putTile(getFileName(fl.getAbsolutePath()), Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fl.getAbsolutePath()), i[0].getTileSize(), i[0].getTileSize(), false));
										}
									};
									f = new BufferedReader(new InputStreamReader(new FileInputStream(new File(i[0].getGamePath() + "/maps/main.s2dge"))));
									String line = f.readLine();
									while(line != null){
										if (line.toString().length() > 0) {
											if (line.toString().equals("# DATA")) {
												h = 1;
											} else if (line.toString().equals("# SPRITES")) {
												h = 2;
											} else if (line.toString().equals("# ENTITIES")) {
												h = 3;
											} else if (line.toString().equals("# TRIGGERS")) {
												h = 4;
											} else if (line.toString().equals("# CONFIG")) {
												h = 5;
											} else if (line.toString().equals("# NPCS")) {
												h = 6;
											} else if (line.toString().length() > 1 && line.toString().substring(0,2).equals("//")) {
											} else if (line.toString().length() > 6) {
												rwog = line.toString().trim().split(" ");
												if (h == 1) {
													i[0].tile[Integer.parseInt(rwog[0])][Integer.parseInt(rwog[1])] = new Tiles(Integer.parseInt(rwog[0]), Integer.parseInt(rwog[1]), rwog[2], rwog[3], rwog[4], i[0].getTileBitmap(rwog[2]), rwog[4].equals("0") ? null : i[0].getTileBitmap(rwog[4]), i[0].getTileSize());
												} else if (h == 2) {
													Sprite tl = new Sprite();
													tl.set(getResizedBitmap(i[0].getBitmap(rwog[2]),Integer.parseInt(rwog[3]),Integer.parseInt(rwog[4]),Integer.parseInt(rwog[5])));
													tl.x = Integer.parseInt(rwog[0])*i[0].getTileSize();
													tl.y = Integer.parseInt(rwog[1])*i[0].getTileSize();
													//if (!rwog[7].equals("-")) tl.hook(rwog[7]);
													i[0].addSpriteData(tl);
												} else if (h == 3) {
													if (rwog[0].equals("Spawn")) {
														i[0].newSpawnPoint();
														i[0].setSpawnX(Integer.parseInt(rwog[1]));
														i[0].setSpawnY(Integer.parseInt(rwog[2]));
													}
												} else if (h == 5) {
													if (rwog[0].equals("Name")) {
														i[0].setCurrentMapName(rwog[1]);
													} else if (rwog[0].equals("Size")) {
														i[0].setCurrentMapSizeX(Integer.parseInt(rwog[1]));
														i[0].setCurrentMapSizeY(Integer.parseInt(rwog[2]));
														if (i[0].getMapSizeX() <= 0 || i[0].getMapSizeY() <= 0) {
															error = "Map row or column cannot be 0 or lower";
														} else {
															i[0].tile = new Tiles[i[0].getMapSizeX()][i[0].getMapSizeY()];
														}
													}
												} else if (h == 6) {
													i[0].addNPC(new NPC(Integer.parseInt(rwog[0]), Integer.parseInt(rwog[1]), rwog[2], rwog[3], rwog[4]));
												}
												rwog = null;
											}
										};
										line = f.readLine();
									}
								} else {
									error = "Tiles folder not found";
								}
							} else {
								error = "Main map file not found";
							}
						} else {
							error = "Maps folder not found";
						}
					} else {
						error = "SFX folder not found";
					}
				} else {
					error = "GFX folder not found";
				};
			} catch (Exception e) {
				error = e.toString();
			};
			f = null;
			i[0].me = new Player(0, 0, "-", "UID test", 0);
			i[0].me.x = i[0].getSpawnX() * i[0].getTileSize();
			i[0].me.y = i[0].getSpawnY() * i[0].getTileSize();
			i[0].setCamX(i[0].me.x - i[0].getScreenXOffset());
			i[0].setCamY(i[0].me.y - i[0].getScreenYOffset());
			return i[0];
		}
		@Override
		public void onPostExecute(Sketchware2DGameEngine a) {
			if (error.equals("-")) {
				a.setFirstRender(true);
                a.setThreadRunning(true);
				a.start();
				a.listener.onLoadCompleted();
			} else {
				a.listener.onLoadFailed(error);
			}
		}

        public int getDen(int a) {
		    return (int)(a * 2.5f);
	    }
	
	    public int getDen(float a) {
		    return (int)(a * 2.5f);
	    }
	
	    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, int rotate) {
		float width = bm.getWidth();
		float height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.setRotate(rotate,width/2,height/2);
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap( bm, 0, 0, (int)width, (int)height, matrix, false);
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight, int rotate) {
		float width = bm.getWidth();
		float height = bm.getHeight();
		float scaleWidth = newWidth / width;
		float scaleHeight = newHeight / height;
		Matrix matrix = new Matrix();
		matrix.setRotate(rotate,width/2,height/2);
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bm, 0, 0, (int)width, (int)height, matrix, false);
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
	}

	}