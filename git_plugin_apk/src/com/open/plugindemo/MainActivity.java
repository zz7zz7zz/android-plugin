package com.open.plugindemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.open.plugindemo.plugin.AbstractPlugin;
import com.open.plugindemo.plugin.PluginMgr;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout mainLayout=(LinearLayout)getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(mainLayout);
		
		
		final ArrayList<AbstractPlugin> ret=PluginMgr.searchPlugin(this);
		
		for(int i=0;i<ret.size();i++)
		{
			Button pluginBtn=new Button(this);
			pluginBtn.setText(ret.get(i).name);
			
			final String className=ret.get(i).activityName;
			final String packageName=ret.get(i).packageName;
			pluginBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					try {
							ComponentName toActivity = new ComponentName(packageName,className);
							Intent intent=new Intent();
					        intent.setComponent(toActivity);
					        startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			mainLayout.addView(pluginBtn, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
	}

	
}
