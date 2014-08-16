package pl.whipsoft.kindleturn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = getLayoutInflater().inflate(R.layout.activity_main, null);
		v.setKeepScreenOn(true);
		setContentView(v);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		String ip = prefs.getString("kindle_ip", "");
		Log.d("KindleTurn", "Kindle IP  = " + ip);
		String username = prefs.getString("username", "");
		Log.d("KindleTurn", "Kindle username  = " + username);
		String password = prefs.getString("password", "");
		Log.d("KindleTurn", "Kindle password  = " + password);

		if (ip.length() > 0 && username.length() > 0 && password.length() > 0) {
			SendCommandTask.setPrefs(ip, username, password);
		} else{
			// Launch settings activity
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int action = event.getAction();
		int keyCode = event.getKeyCode();
		Log.d("SWITCH", "keycode= " + keyCode);

		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			if (action == KeyEvent.ACTION_UP) {
				Log.i("KindleTurn", "NEXT");
				new SendCommandTask().execute(COMMAND.NEXT_PAGE);
			}
			return true;
		case KeyEvent.KEYCODE_ENTER:
			if (action == KeyEvent.ACTION_DOWN) {
				Log.i("KindleTurn", "PREV");
				new SendCommandTask().execute(COMMAND.PREVIOUS_PAGE);
			}
			return true;
		default:
			return super.dispatchKeyEvent(event);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			// Launch settings activity
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			break;
		}

		return true;
	}

}
