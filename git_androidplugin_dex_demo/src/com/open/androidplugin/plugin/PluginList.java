/**
 * 
 */
package com.open.androidplugin.plugin;

import java.util.ArrayList;

/**
 * A simple list of initialized plugins
 * @author yanglonghui
 *
 */
public final class PluginList {
	
    private ArrayList<PluginBean> mPlugins;

    public PluginList() 
    {
        mPlugins = new ArrayList<PluginBean>();
        //这里加载插件列表
    }

    public synchronized ArrayList<PluginBean> getList() 
    {
        return mPlugins;
    }

    public synchronized void addPlugin(PluginBean plugin) 
    {
        if (!mPlugins.contains(plugin)) 
        {
            mPlugins.add(plugin);
        }
    }

    public synchronized void removePlugin(PluginBean plugin) 
    {
        int location = mPlugins.indexOf(plugin);
        if (location != -1) 
        {
            mPlugins.remove(location);
        }
    }
    
    public synchronized PluginBean getPlugin(int index)
    {
    	if(index>mPlugins.size()||index<-1)
    	{
    		throw new IndexOutOfBoundsException("Invalid index " + index+ ", size is " + mPlugins.size());
    	}
		return null;
    	
    }

    public synchronized void clear() 
    {
        mPlugins.clear();
    }

}