package com.example.camerademo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.joya.matchthesunset.R;

public class WebActivity extends Activity{
	
	 WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webpage);
		  mWebView=(WebView) findViewById(R.id.wee);
		    mWebView.loadUrl("http://www.groupshot.us/");
			mWebView.setWebViewClient(new FbWebViewClient());
			mWebView.getSettings().setJavaScriptEnabled(true);
	}

	 private class FbWebViewClient extends WebViewClient {

	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    
	            Log.d("******", "shouldOverrideUrlLoading");
	            
	                mWebView.loadUrl(url);
	             
	            return true;
	        }

	        @Override
	        public void onReceivedError(WebView view, int errorCode,
	                String description, String failingUrl) {
	            super.onReceivedError(view, errorCode, description, failingUrl);
	        }

	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	           
	           
	            super.onPageStarted(view, url, favicon);
	           
	        }

	        @Override
	        public void onPageFinished(WebView view, String url) {
	            super.onPageFinished(view, url);
	    
	        }
	    }
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		com.facebook.AppEventsLogger.activateApp(getApplicationContext(), "1378212579084638");
	}

}
