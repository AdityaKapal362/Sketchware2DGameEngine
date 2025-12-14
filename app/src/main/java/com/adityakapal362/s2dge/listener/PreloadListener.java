package com.adityakapal362.s2dge.listener;

interface PreloadListener {
	void onLoadStart(int max);
	void onLoadProgress(int progress);
	void onLoadCompleted();
	void onLoadFailed(String error);
}