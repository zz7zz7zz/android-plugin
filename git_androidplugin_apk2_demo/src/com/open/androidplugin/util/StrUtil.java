package com.open.androidplugin.util;

/**
 * @author yanglonghui
 *
 */
public class StrUtil {
	
	/**
	 * 判断字符串是否为空,true表示字符串为空，或者为空字符串,false表示字符串不为空，长度大于0
	 * @param str
	 * @return
	 */
    public static boolean isEmpty(CharSequence str) 
    {
        if (str == null || str.length() == 0)
        {
        	return true;
        }            
        else
        {
        	return false;
        }
    }
}
