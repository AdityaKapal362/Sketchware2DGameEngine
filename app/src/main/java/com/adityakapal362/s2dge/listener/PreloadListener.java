package com.adityakapal362.s2dge.listener;

public interface OnPreloadListener {
	void onLoadStart(int max);
	void onLoadProgress(int progress);
	void onLoadCompleted();
	void onLoadFailed(String error);
}