/**
 * 
 */
package com.open.androidplugin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.open.androidplugin.PluginApp;

import android.os.Environment;

/**
 * @author yanglonghui
 *
 */
public final class FileUtil {
	
	// ------------------------------ 手机系统相关 ------------------------------
	/**SD卡路径*/
	public static String SDCARD_PAHT = Environment.getExternalStorageDirectory().getPath();
	
	/**本地路径*/
	public static String LOCAL_PATH = PluginApp.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
	
	/**当前的路径,如果有SD卡的时候当前路径为SD卡，如果没有的话则为程序的私有目录*/
	public static String CURRENT_PATH = "";

	// 判断有没有SD卡,确定当前的目录
	static 
	{
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) 
		{
			CURRENT_PATH = SDCARD_PAHT;
		} 
		else 
		{
			CURRENT_PATH = LOCAL_PATH;
		}
	}
	
	/**系统的反斜杠*/
	public static String FILESPARATOR = File.separator;
	
	/**系统的换行符*/
	public static String NEWLINE=System.getProperty("line.separator");
	
	/**程序的根目录*/
	public static String APPROOT="pluginroot";
	
	//------------------------------------插件相关的目录以及路径--------------------------------------------
	/**插件的存放目录*/
	public static String SUFFIX_PLUGIN_PATH=FILESPARATOR+APPROOT+FILESPARATOR+"plugins"+FILESPARATOR;
	public static String PLUGIN_PATH=LOCAL_PATH+SUFFIX_PLUGIN_PATH;
	public static String PLUGIN_PATH_SD=SDCARD_PAHT+SUFFIX_PLUGIN_PATH;
	
	//------------------------------------文件的相关方法--------------------------------------------
	/**
	 * 将数据写入一个文件
	 * @param destFilePath  要创建的文件的路径
	 * @param data 待写入的文件数据
	 * @param startPos 起始偏移量
	 * @param length 要写入的数据长度
	 * @return 成功写入文件返回true,失败返回false
	 */
	public static boolean writeFile(String destFilePath,byte[] data,int startPos,int length)
	{
		try {
				if(!createFile(destFilePath))
				{
					return false;
				}
				FileOutputStream fos = new FileOutputStream(destFilePath);
				fos.write(data, startPos, length);
				fos.flush();
				if (null != fos) 
				{
					fos.close();
					fos = null;
				}
				return true;
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 从一个输入流里写文件
	 * @param destFilePath 要创建的文件的路径
	 * @param in 要读取的输入流
	 * @return 写入成功返回true,写入失败返回false
	 */
	public static boolean writeFile(String destFilePath,InputStream in)
	{
		try {
				if(!createFile(destFilePath))
				{
					return false;
				}
				FileOutputStream fos = new FileOutputStream(destFilePath);
				int readCount = 0;
				int len = 1024;
				byte[] buffer = new byte[len];
				while ((readCount = in.read(buffer)) != -1)
				{
					fos.write(buffer,0,readCount); 
				}
				fos.flush();
				if (null != fos) 
				{
					fos.close();
					fos = null;
				}
				if(null!=in)
				{
					in.close();
					in=null;
				}
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 读取文件，返回以byte数组形式的数据
	 * @param filePath 要读取的文件路径名
	 * @return
	 */
	public static byte[] readFile(String filePath){
		if(isFileExist(filePath))
		{
			try {
				FileInputStream fi= new FileInputStream(filePath);
				return readFile(fi);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 从一个数量流里读取数据,返回以byte数组形式的数据
	 * @param in 要读取的输入流
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFile(InputStream in)
	{
		try {
				byte[] b;
				b = new byte[in.available()];
				in.read(b);
				if(null!=in)
				{
					in.close();
					in=null;
				}	
				return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将一个文件拷贝到另外一个地方,如果目的文件存在，则会替换
	 * @param sourceFile 源文件地址
	 * @param destFile 目的地址
	 * @return 拷贝成功返回true,失败返回false
	 */
	public static boolean copyFiles(String sourceFile,String destFile)
	{
		try {
			FileInputStream fi= new FileInputStream(sourceFile);
			writeFile(destFile,fi);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断文件是否存在
	 * @param filePath 路径名
	 * @return
	 */
	public static boolean isFileExist(String filePath)
	{
		File file=new File(filePath);
		return file.exists();
	}
	
	/**
	 * 创建一个文件，创建成功返回true
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath)
	{
		try {
			File file = new File(filePath);
			if (!file.exists()) 
			{
				if (!file.getParentFile().exists()) 
				{
					file.getParentFile().mkdirs();
				}

				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 删除一个文件
	 * @param filePath 要删除的文件路径名
	 * @return true if this file was deleted, false otherwise
	 */
	public static boolean deleteFile(String filePath)
	{
		File file=new File(filePath);
		if(file.exists())
		{
			return file.delete();
		}
		return false;
	}
	
}
