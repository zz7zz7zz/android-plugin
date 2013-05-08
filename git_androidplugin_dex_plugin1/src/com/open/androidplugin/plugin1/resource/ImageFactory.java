/**
 * 
 */
package com.open.androidplugin.plugin1.resource;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Administrator
 *
 */
public class ImageFactory {
	
	private static HashMap<String,Bitmap> imgMap=new HashMap<String,Bitmap>();
	
	public static Bitmap getImage(String bitmapName)
	{
		Bitmap bm=imgMap.get(bitmapName);
		if(null==bm)
		{
			bm=BitmapFactory.decodeStream(ImageFactory.class.getResourceAsStream("images/"+bitmapName));
			imgMap.put(bitmapName, bm);
			android.util.Log.v("new", "new");
		}
		else
		{
			android.util.Log.v("old", "old");
		}
		return bm;
	}
	
	public static void destory()
	{
		imgMap.clear();
		imgMap=null;
	}
}
