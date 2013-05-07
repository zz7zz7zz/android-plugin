package com.open.plugindemo.plugin;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PluginMgr {
	
	public static ArrayList<AbstractPlugin> searchPlugin(Context context)
	{
		ArrayList<AbstractPlugin> ret=new ArrayList<AbstractPlugin>();
		
		PackageManager pm=context.getPackageManager();
		List<PackageInfo> pkgs=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		
		for(PackageInfo pkg	:pkgs)
		{
			//包名
			String packageName=pkg.packageName;
			String sharedUserId= pkg.sharedUserId;
			
			//sharedUserId是开发时约定好的，这样判断是否为自己人
			if(!"com.open.plugin.shareUserId".equals(sharedUserId)||"com.open.plugindemo".equals(packageName))
				continue;
			
			AbstractPlugin plug=new AbstractPlugin();
			plug.name=pm.getApplicationLabel(pkg.applicationInfo).toString();
			plug.packageName=packageName;
			plug.activityName=String.format("%s.%s", packageName,"MainUI");
			
			ret.add(plug);
		}
		return ret;
	}
}
