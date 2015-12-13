/**
 * 
 */
package com.open.androidplugin.plugin2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * @author Administrator
 *
 */
public class WebSitePluginView extends LinearLayout {

    
	public WebSitePluginView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}

	public WebSitePluginView(Context context) {
		super(context);
		
		init(context);
	}
	
	private void init(Context context)
	{
		WebView mWebView = new WebView(context);  
		mWebView.setHorizontalScrollBarEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY) ;//去掉右边白色边框
		WebSettings mWebSettings = mWebView.getSettings();
		mWebSettings.setJavaScriptEnabled(true);
		mWebSettings.setSupportZoom(true);
		mWebSettings.setBuiltInZoomControls(true);// 设置支持缩放
		mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);     
				return true; 
			}
			
		});
		mWebView.loadUrl("http://www.baidu.com"); 

		this.addView(mWebView,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
}
