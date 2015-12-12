package com.open.androidplugin.plugin1;

import android.content.Context;
import android.os.Bundle;

import com.open.androidplugin.plugin.AbstractPlugin;

public class ImagePlugin extends AbstractPlugin<ImagePluginView> {

	public ImagePlugin(Context mContext) {
		super(mContext);
		this.setContent(new ImagePluginView(mContext));
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
