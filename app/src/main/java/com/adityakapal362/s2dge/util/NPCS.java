package com.adityakapal362.s2dge.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;

import com.adityakapal362.s2dge.Sketchware2DGameEngine;

public class NPCS {

    private static NPCS instance;
    private ArrayList<NPC> npcList;
    private HashMap<String, Bitmap> npcBitmaps;
    private int npcANIM_ID = 0;
    public Sketchware2DGameEngine surface;

    public static synchronized NPCS getInstance(Context a) {
        if (instance == null) instance = new NPCS(a.getApplicationContext());
        return instance;
    }

    public NPCS(Context a, Sketchware2DGameEngine b) {
        npcList = new ArrayList<>();
        npcBitmaps = new HashMap<>();
    }

    public void animateNPCS() {
        npcANIM_ID++;
        for (NPC npc : npcList) {
            npc.set(surface.spriteBitmaps.get("pl_i_r_" + (npcANIM_ID / 4)));
        }
        if (npcANIM_ID >= 16) npcANIM_ID = 0;
    }

    public void drawNPCS(Canvas a, int b, int c) {
        for (NPC npc : npcList) {
            npc.drawBodyAndName(a, b, c);
        }
    }

    public void addNPC(NPC npc) {
        npc.set(surface.spriteBitmaps.get("pl_i_r_1"));
        npc.x = npc.tx * surface.tileSize;
        npc.y = npc.ty * surface.tileSize;
        npc.bodyWidth = (int) (npc.bitmap.getWidth() / 2);
		npc.bodyHeight = (int) (npc.bitmap.getHeight() / 2);
        npcList.add(npc);
    }

}