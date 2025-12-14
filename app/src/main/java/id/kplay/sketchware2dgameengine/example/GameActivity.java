package id.kplay.sketchware2dgameengine.example;

import android.app.Activity;
import android.os.Bundle;
import com.adityakapal362.s2dge.Sketchware2DGameEngine;
import com.adityakapal362.s2dge.listener.PreloadListener;
import com.adityakapal362.s2dge.util.Player;
import com.adityakapal362.s2dge.util.FileUtil;
import android.widget.LinearLayout;

public class GameActivity extends Activity {
    
    public Sketchware2DGameEngine game;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Sketchware2DGameEngine(this);
        game.setGameFilePath(FileUtil.getPackageDir(getApplicationContext()) + "/resources");
        game.setPreloadListener(new PreloadListener() {
                @Override
                public void onLoadStart(int max) {
                }
                @Override
                public void onLoadProgress(int progress) {
                }
                @Override
                public void onLoadCompleted() {
                    setContentView(game);
                }
                @Override
                public void onLoadFailed(String error) {
                }
            });
        game.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        game.setShowFps(true);
        setContentView(game);
        game.startEngine();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}