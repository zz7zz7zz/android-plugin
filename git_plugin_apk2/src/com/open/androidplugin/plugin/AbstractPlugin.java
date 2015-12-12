/**
 * 
 */
package com.open.androidplugin.plugin;

import android.content.Context;
import android.os.Bundle;

/**
 * @author yanglonghui
 *
 */
public abstract class AbstractPlugin<E> {
	
	protected Context mContext;
	protected E mPluginContent=null;
	
	public AbstractPlugin(Context mContext)
	{
		this.mContext=mContext;
	}
	
	protected void setContent(E content)
	{
		if(null!=content)
		{
			this.mPluginContent=content;
		}
		else
		{
			throw new NullPointerException("contentView");
		}
	}
	
	protected E getContent()
	{
		return mPluginContent;
	}
	
	protected abstract void onCreate(Bundle savedInstanceState);
	
	protected abstract void onResume();
	
	protected abstract void onStop();
	
	protected abstract void onDestroy();
}
