package com.adityakapal362.s2dge.async;

import android.view.Choreographer;
import java.util.concurrent.Semaphore;

public final class VsyncPacer implements Choreographer.FrameCallback {
    private final Semaphore tick = new Semaphore(0);
    private volatile boolean running = false;

    public void start() {
        if (running) return;
        running = true;
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void stop() {
        running = false;
    }

    public void await() throws InterruptedException {
        tick.acquire();
    }

    @Override public void doFrame(long frameTimeNanos) {
        if (!running) return;
        tick.release();
        Choreographer.getInstance().postFrameCallback(this);
    }
}
