package com.open.androidplugin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.open.androidplugin.plugin3.SnakeView;

public class SnakepluginActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
//        GameView mGameView=new GameView(this);
        SnakeView mGameView=new SnakeView (this,null);
        setContentView(mGameView);
    }
}