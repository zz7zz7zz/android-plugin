/**
 * 
 */
package com.open.androidplugin.plugin;

import java.util.HashMap;

/**
 * @author yanglonghui
 *
 */
public final class PluginMgr {
	
	static PluginMgr single=new PluginMgr();
	
	private HashMap<String,AbstractPlugin> mPluginMap=new HashMap<String,AbstractPlugin>();//存放程序运行过的插件
	
	public static PluginMgr getInstance()
	{
		return single;		
	}
}
