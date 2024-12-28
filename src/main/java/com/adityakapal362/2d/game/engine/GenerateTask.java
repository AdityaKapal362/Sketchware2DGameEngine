package com.adityakapal362.2d.game.engine;

import android.os.AsyncTask;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;

public class GenerateTask extends AsyncTask<Sketchware,2DGameEngine, Void, Sketchware2DGameEngine> {
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
			File pafr = new File(i[0].gamePath + "/gfx");
			if (pafr.exists()) {
				File[] lf1 = pafr.listFiles();
				for (File fl : lf1) {
					if (fl.isFile()) {
						if (getFileName(fl.getAbsolutePath()).length() > 2 && getFileName(fl.getAbsolutePath()).substring(0,3).equals("pl_")) {
							Bitmap kqpd = BitmapFactory.decodeFile(fl.getAbsolutePath());
							i[0].spriteBitmaps.put(getFileName(fl.getAbsolutePath()), Bitmap.createScaledBitmap(kqpd, (int)getDen(kqpd.getWidth()*2.2f), (int)getDen(kqpd.getHeight()*2.2f), false));
							kqpd = null;
						} else {
							i[0].spriteBitmaps.put(getFileName(fl.getAbsolutePath()), BitmapFactory.decodeFile(fl.getAbsolutePath()));
						}
					}
				};
				pafr = new File(i[0].gamePath + "/sfx");
				if (pafr.exists() && pafr.isDirectory()) {
					File[] lf2 = pafr.listFiles();
					for (File fl : lf2) {
						if (fl.isFile()) {
							//sfx nya dittt
						}
					};
					pafr = new File(i[0].gamePath + "/maps");
					if (pafr.exists() && pafr.isDirectory()) {
						File pafr2 = new File(i[0].gamePath + "/maps/main.s2dge");
				        if (pafr2.exists() && pafr2.isFile()) {
							pafr = new File(i[0].gamePath + "/tiles");
							if (pafr.exists() && pafr.isDirectory()) {
								File[] lf3 = pafr.listFiles();
								for (File fl : lf3) {
								    if (fl.isFile()) {
									    i[0].tileBitmaps.put(getFileName(fl.getAbsolutePath()), Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fl.getAbsolutePath()), (int)i[0].pixell, (int)i[0].pixell,false));
									}
								}
								f = new BufferedReader(new InputStreamReader(new java.io.FileInputStream(new java.io.File(i[0].gamePath + "/maps/main.s2dge"))));
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
												i[0].tile[Integer.parseInt(rwog[0])][Integer.parseInt(rwog[1])] = new Tiles(Integer.parseInt(rwog[0]), Integer.parseInt(rwog[1]), rwog[2], rwog[3], rwog[4]);
											} else if (h == 2) {
												Sprite tl = new Sprite();
												tl.set(getResizedBitmap(i[0].spriteBitmaps.get(rwog[2]),Integer.parseInt(rwog[3]),Integer.parseInt(rwog[4]),Integer.parseInt(rwog[5])));
												tl.x = getDen(Integer.parseInt(rwog[0])*i[0].pixel);
												tl.y = getDen(Integer.parseInt(rwog[1])*i[0].pixel);
												//if (!rwog[7].equals("-")) tl.hook(rwog[7]);
												i[0].spritesData.add(tl);
											} else if (h == 3) {
												if (rwog[0].equals("Spawn")) {
													i[0].spawn = new Point();
													i[0].spawn.x = Integer.parseInt(rwog[1]);
													i[0].spawn.y = Integer.parseInt(rwog[2]);
													i[0].camX = getDen(i[0].spawn.x * i[0].pixel) + i[0].screenXOffset;
													i[0].camY = getDen(i[0].spawn.y * i[0].pixel) + i[0].screenYOffset;
												}
											} else if (h == 5) {
												if (rwog[0].equals("Name")) {
													i[0].currentMapName = rwog[1];
												} else if (rwog[0].equals("Size")) {
													i[0].mapSizeX = Integer.parseInt(rwog[1]);
													i[0].mapSizeY = Integer.parseInt(rwog[2]);
													if (i[0].mapSizeX <= 0 || i[0].mapSizeY <= 0) {
														error = "Map row or column cannot be 0 or lower";
													} else {
														i[0].tile = new Tiles[i[0].mapSizeX][i[0].mapSizeY];
													}
												}
											} else if (h == 6) {
											    if (i[0].npcs == null) i[0].npcs = new ArrayList<>();
												NPC nn = new NPC(Integer.parseInt(rwog[0]), Integer.parseInt(rwog[1]), rwog[2], rwog[3], rwog[4]);
												i[0].npcs.add(nn);
											}
											rwog = null;
										}
									}
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
		}
		f = null;
		me = new Player("AdityaKapal362", "UID test", 0);
		me.x = getDen(i[0].spawn.x * i[0].pixel);
		me.y = getDen(i[0].spawn.y * i[0].pixel);
		return i[0];
	}

	@Override
    public void onPostExecute(Sketchware2DGameEngine a) {
		if (error.equals("-")) {
			a.firstRen = true;
			a.listener.onGenerateCompleted();
		} else {
			a.listener.onGenerateFailed(error);
		}
	}
}