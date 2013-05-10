/**
 * 
 */
package com.open.androidplugin.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.open.androidplugin.plugin.PluginBean;

/**
 * @author yanglonghui
 *
 */
public class PluginListParser {
	
	/**
	 * 解析插件信息文件
	 * @param in
	 * @return
	 */
	public ArrayList<PluginBean> read(InputStream in)
	{
		ArrayList<PluginBean> pluginList=null;
		PluginBean mPluginBean=null;
		
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "UTF-8");
			int type = parser.getEventType(); 
			
			while(type!=XmlPullParser.END_DOCUMENT)
			{	
				switch(type)
				{
					case XmlPullParser.START_DOCUMENT:
						pluginList=new ArrayList<PluginBean>();
						break;
					case XmlPullParser.START_TAG:
						if("plugin".equals(parser.getName()))
						{
							mPluginBean=new PluginBean();
							type=parser.next();
							while(!((type==XmlPullParser.END_TAG)&&("plugin".equals(parser.getName()))))
							{
								if(type==XmlPullParser.START_TAG)
								{
									if("id".equals(parser.getName()))
									{
										mPluginBean.setId(parser.nextText());
									}
									else if("authorid".equals(parser.getName()))
									{
										mPluginBean.setAuthorID(parser.nextText());
									}
									else if("typeid".equals(parser.getName()))
									{
										mPluginBean.setTypeID(parser.nextText());
									}
									else if("name".equals(parser.getName()))
									{
										mPluginBean.setName(parser.nextText());
									}
									else if("icon".equals(parser.getName()))
									{
										mPluginBean.setIcon(parser.nextText());
									}
									else if("description".equals(parser.getName()))
									{
										mPluginBean.setDescription(parser.nextText());
									}
									else if("updateTime".equals(parser.getName()))
									{
										mPluginBean.setUpdateTime(parser.nextText());
									}
									else if("signature".equals(parser.getName()))
									{
										mPluginBean.setSignature(parser.nextText());
									}
									else if("version".equals(parser.getName()))
									{
										mPluginBean.setVersion(parser.nextText());
									}
									else if("path".equals(parser.getName()))
									{
										mPluginBean.setPath(parser.nextText());
									}
									else if("filename".equals(parser.getName()))
									{
										mPluginBean.setFileName(parser.nextText());
									}
									else if("classname".equals(parser.getName()))
									{
										mPluginBean.setClassName(parser.nextText());
									}
								}
								type=parser.next();
							}
							pluginList.add(mPluginBean);
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}	
				type=parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			if(in!=null)
			{
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in=null;
			}
		}
		
		return pluginList;	
	}
}
