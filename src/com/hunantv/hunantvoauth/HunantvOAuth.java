package com.hunantv.hunantvoauth;

import com.hunantv.hunnatvoauth.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HunantvOAuth extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnStartOAuth = (Button) findViewById(R.id.start_oauth);
		btnStartOAuth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.start_oauth:
					Log.i(TAG, "-------------start_oauth--------------");
					Intent intent = new Intent(HunantvOAuth.this,
							OAuthActivity.class);
					intent.putExtra("from", "hunantv");
					// startActivity(intent);
					startActivityForResult(intent, 1);
					break;
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "-------------" + requestCode + "--------------");
		switch (requestCode) {
		// case RESULT_OK:
		case 1:
			Log.i(TAG, "-------------RESULT_OK--------------");
			Bundle bundle = data.getExtras();
			String openid = bundle.getString("openid");
			String access_token = bundle.getString("access_token");
			String nickname = bundle.getString("nickname");

			TextView openid_text = (TextView) findViewById(R.id.openid);
			TextView access_token_text = (TextView) findViewById(R.id.access_token);
			TextView nickname_text = (TextView) findViewById(R.id.nickname);

			openid_text.setText("openid: " + openid);
			access_token_text.setText("access_token: " + access_token);
			nickname_text.setText("nickname: " + nickname);

			Toast.makeText(getApplicationContext(), "授权流程完成", Toast.LENGTH_LONG)
					.show();

			break;
		default:
			break;
		}
		/*
		 * if (requestCode == 1) { if (resultCode == OAuthActivity.RESULT_CODE)
		 * { Log.i(TAG, "-------------RESULT_CODE--------------"); } }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Toast.makeText(getApplicationContext(), item.getItemId(),
		// Toast.LENGTH_LONG).show();
		switch (item.getItemId()) {
		case R.id.action_exit:
			HunantvOAuth.this.finish();
			break;
		}
		return true;
	}

}
