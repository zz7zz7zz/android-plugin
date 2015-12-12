/**
 * 
 */
package com.open.androidplugin.plugin2;

import android.content.Context;
import android.os.Bundle;

import com.open.androidplugin.plugin.AbstractPlugin;

/**
 * @author Administrator
 *
 */
public class WebSitePlugin extends AbstractPlugin<WebSitePluginView> {

	public WebSitePlugin(Context mContext) {
		super(mContext);
		this.setContent(new WebSitePluginView(mContext));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}
}
