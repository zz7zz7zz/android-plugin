/**
 * 
 */
package com.open.androidplugin.plugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;

import com.open.androidplugin.util.FileUtil;

import dalvik.system.DexClassLoader;

/**
 * 
 * @author yanglonghui
 *
 */
public final class PluginFactory {
	
	/**
	 * 
	 * @param context 上下文
	 * @param plugin  要产出的插件
	 * @return
	 */
	static final AbstractPlugin produce(Context context,PluginBean plugin)
	{
		AbstractPlugin mParentPlugin=null;
		
		File pluginFile=new File(FileUtil.PLUGIN_PATH_SD+plugin.getFileName());
		
		if(pluginFile.exists())
		{
			DexClassLoader mDexClassLoader=new DexClassLoader(pluginFile.toString(), FileUtil.PLUGIN_PATH_SD, null, context.getClass().getClassLoader());
			if(null!=mDexClassLoader)
			{
				try {
					Class<?> mClass=mDexClassLoader.loadClass(plugin.getClassName());
					if(null!=mClass)
					{
						Class<?> cs[] = {android.content.Context.class};
						Constructor<?> mConstructor=mClass.getConstructor(cs);
						if(null!=mConstructor)
						{
							mParentPlugin=(AbstractPlugin)mConstructor.newInstance(context);
						}
					}				
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		return mParentPlugin;		
	}
}
