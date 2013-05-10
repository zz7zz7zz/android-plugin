package com.open.androidplugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.open.androidplugin.plugin.PluginBean;
import com.open.androidplugin.plugin.PluginUI;
import com.open.androidplugin.util.FileUtil;
import com.open.androidplugin.xmlparser.PluginListParser;

public class MainActivity extends Activity {
	
	ArrayList<PluginBean> list=new ArrayList<PluginBean>(20);
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
       
        InputStream in=this.getResources().openRawResource(R.raw.pluginlist);
        PluginListParser mPluginListParser=new PluginListParser();
        list=mPluginListParser.read(in);
        
        searchPlugins();
        
        PluginAdapter mPluginAdapter=new PluginAdapter(this, list);        
        GridView mGridview = (GridView) findViewById(R.id.gridview); 
        mGridview.setAdapter(mPluginAdapter);
        
        mGridview.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
//				Toast.makeText(MainActivity.this, ""+position+"\n"+list.get(position).getmPath()+"\n"+list.get(position).getmClassName(), Toast.LENGTH_LONG).show();

				Intent mIntent=new Intent(MainActivity.this,PluginUI.class);
				Bundle mBundle = new Bundle();  
		        mBundle.putSerializable("plugin",list.get(position));  
		        mIntent.putExtras(mBundle);

				startActivity(mIntent);
			}
		});
        
    }
    
    private void searchPlugins()
    {
        AssetManager am = this.getResources().getAssets();
        int size=list.size();
       
        for(int i=0;i<size;i++)
        {
        	String filename=list.get(i).getFileName();
        	try {
				InputStream is =am.open("jar/"+filename);
				if(null!=is)
				{
					if(!FileUtil.isFileExist(FileUtil.PLUGIN_PATH+filename))
					{
						FileUtil.writeFile(FileUtil.PLUGIN_PATH+filename, is);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public class PluginAdapter extends BaseAdapter
    {
    	private Context mContext;
    	private ArrayList<PluginBean> pluginList;
    	private LayoutInflater mInflater;
    	
    	public PluginAdapter(Context mContext, ArrayList<PluginBean> pluginList) 
    	{
    		this.mContext = mContext;
    		this.pluginList = pluginList;
    		mInflater = LayoutInflater.from(this.mContext);
    	}

    	@Override
    	public int getCount() {
    		return pluginList.size();
    	}

    	@Override
    	public Object getItem(int position) {
    		return pluginList.get(position);
    	}

    	@Override
    	public long getItemId(int position) {
    		
    		return position;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) 
    	{
    		if(null==convertView)
    		{
    			convertView=mInflater.inflate(R.layout.pluginitem, null);
    		}		
    		convertView.setBackgroundResource(R.drawable.icon1+position);
    		TextView tv=(TextView)convertView.findViewById(R.id.pluginname);
    		tv.setText(pluginList.get(position).getName());
    		return convertView;
    	}
    	
    }
    
}