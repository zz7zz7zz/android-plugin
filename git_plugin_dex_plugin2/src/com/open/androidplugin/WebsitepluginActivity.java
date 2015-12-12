package com.open.androidplugin;

import android.app.Activity;
import android.os.Bundle;

import com.open.androidplugin.plugin2.WebSitePluginView;

public class WebsitepluginActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        setContentView(new WebSitePluginView(this));
    }
}