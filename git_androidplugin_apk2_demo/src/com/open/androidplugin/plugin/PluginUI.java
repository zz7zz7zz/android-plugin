/**
 * 
 */
package com.open.androidplugin.plugin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.open.androidplugin.R;

/**
 * @author yanglonghui
 *
 */
public final class PluginUI extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pluginui);
		
		Intent mIntent=this.getIntent();
		Bundle bundle=mIntent.getExtras();
		if(null!=bundle)
		{
			PluginBean mPlugin=(PluginBean) bundle.get("plugin");
			AbstractPlugin mParentPlugin=PluginFactory.produce(this,mPlugin);
			if(null!=mParentPlugin)
			{
				if(mParentPlugin.getContent() instanceof View)
				{
					this.setContentView((View)mParentPlugin.getContent());
				}
				else if(mParentPlugin.getContent() instanceof Fragment)
				{
					getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, (Fragment)mParentPlugin.getContent())
					.commit();
				}
			}
			else
			{
				Toast.makeText(this,"PluginUIFragment plugin is null,please check you code", Toast.LENGTH_LONG).show();
				this.finish();
			}
		}
		else
		{
			this.finish();
		}
	}
	
}
