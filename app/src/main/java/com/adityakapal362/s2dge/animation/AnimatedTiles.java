package com.adityakapal362.s2dge.animation;

import com.adityakapal362.s2dge.util.Tiles;

public class AnimatedTiles {
    
    public int frameCount = 1;
    public int currentFrame = 0;
    public int frameDelay = 5;
    public int frameTicker = 0;
    
    public AnimatedTiles() {
    }
    
    public void update() {
        frameTicker++;
        if (frameTicker >= frameDelay) {
            frameTicker = 0;
            currentFrame++;
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }
    }
    
}