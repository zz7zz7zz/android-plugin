/**
 * 
 */
package com.open.androidplugin.plugin3.resource;

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
			bm=BitmapFactory.decodeStream(ImageFactory.class.getResourceAsStream("drawables/"+bitmapName));
			imgMap.put(bitmapName, bm);
		}
		return bm;
	}
	
	
	public static void destory()
	{
		imgMap.clear();
		imgMap=null;
	}
}
