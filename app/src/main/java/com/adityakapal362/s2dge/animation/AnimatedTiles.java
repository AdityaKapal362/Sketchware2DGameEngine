package com.adityakapal362.s2dge.animation;

public class AnimatedTiles extends Tiles {
    
    public int frameCount = 1;
    public int currentFrame = 0;
    public int frameDelay = 5;
    public int frameTicker = 0;
    
    public AnimatedTiles() {
        super();
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