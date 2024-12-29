package com.adityakapal362.2d.game.engine.listener;

interface OnGenerateListener {
    void onGenerateStart(int max);
    void onGenerateProgress(int progress);
    void onGenerateCompleted();
    void onGenerrateFailed(String error);
}