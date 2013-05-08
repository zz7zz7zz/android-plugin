package com.open.androidplugin;

import com.open.androidplugin.plugin1.ImagePluginView;

import android.app.Activity;
import android.os.Bundle;

public class ImagepluginActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setContentView(new ImagePluginView(this));
        
    }
}