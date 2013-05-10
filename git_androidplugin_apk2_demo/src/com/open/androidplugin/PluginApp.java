/**
 * 
 */
package com.open.androidplugin;

import android.app.Application;

/**
 * @author yanglonghui
 *
 */
public class PluginApp extends Application {

	private static PluginApp instance;
		
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static PluginApp getInstance()
    {
        return instance;
    }
    
}