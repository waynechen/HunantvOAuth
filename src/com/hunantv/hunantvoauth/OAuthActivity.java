package com.hunantv.hunantvoauth;

import com.hunantv.hunnatvoauth.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class OAuthActivity extends Activity {
	public final static int RESULT_CODE = 2;
    private static final String TAG = "OAuthActivity";
    
    private static final String OAUTH_URL = "http://oauth.hunantv.com/qq/mlogin";
    
    private String OPENID = "";
    private String ACCESS_TOKEN = "";
    private String NICKNAME = "";
    
    Handler handler = new Handler();
    private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "-------------onCreate--------------");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth);
		webview = (WebView) findViewById(R.id.webView1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.requestFocusFromTouch();
        //设置Web视图
		webview.setWebViewClient(new OAuthWebViewClient());
		
		webview.addJavascriptInterface(new Object() {
			@SuppressWarnings("unused")
			public void clickOnAndroid(String openid, String access_token, String nickname) {
				OPENID = openid;
				ACCESS_TOKEN = access_token;
				NICKNAME = nickname;
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), R.string.oauth_succeed, Toast.LENGTH_SHORT).show();
						
						Intent intent = new Intent();  
			            intent.setClass(OAuthActivity.this, MainActivity.class);
			            Bundle bundle = new Bundle();
			            bundle.putString("openid", OPENID);
			            bundle.putString("access_token", ACCESS_TOKEN);
			            bundle.putString("nickname", NICKNAME);
			            intent.putExtras(bundle);
			            
			            //startActivityForResult(intent, 0);
			            
			            OAuthActivity.this.setResult(1, intent);
			            OAuthActivity.this.finish();
					}
				});
			}
		}, "demo");
		
		webview.loadUrl(OAUTH_URL);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
		 * webview.goBack(); return true; }
		 */
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent intent = new Intent();
			intent.setClass(OAuthActivity.this, MainActivity.class);
			startActivity(intent);
			OAuthActivity.this.finish();
		}
		return false;
	}
	

	//Web视图 
    private class OAuthWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

/*		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
			return true;
		}*/
		
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.oauth, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_back:
			OAuthActivity.this.finish();
			break;
		}
		return true;
	}

}
