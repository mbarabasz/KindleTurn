package pl.whipsoft.kindleturn;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
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

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int action = event.getAction();
		int keyCode = event.getKeyCode();
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			if (action == KeyEvent.ACTION_UP) {
				Log.i("KindleTurn", "NEXT");
				new SendCommandTask().execute(COMMAND.NEXT_PAGE);
			}
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
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

}
