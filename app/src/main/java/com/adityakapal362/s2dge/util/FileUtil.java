package com.adityakapal362.s2dge.util;

import android.content.Context;

public class FileUtil {
    public static String getPackageDir(Context a) {
        return a.getExternalFilesDir(null).getAbsolutePath();
    }
}