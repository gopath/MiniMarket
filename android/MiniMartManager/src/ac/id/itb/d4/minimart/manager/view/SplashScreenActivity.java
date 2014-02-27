package ac.id.itb.d4.minimart.manager.view;


import ac.id.itb.d4.minimart.manager.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

public class SplashScreenActivity extends Activity {

	private final SplashScreenActivity self = this;
	private long SLEEP_TIME = 5;
	private SharedPreferences sessions;	
	private IntentLauncher launcher;
	private ProgressBar progressBar;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_splash);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setVisibility(View.INVISIBLE);	
		
		ProgressLoadingTask loading = new ProgressLoadingTask();
		loading.start();
	}
	
	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			
			sessions = getSharedPreferences("SESSION", 0);
			if(sessions.contains("isLogin")){
				Intent intent = new Intent(self, MiniMartManagerMainActivity.class);
				startActivity(intent);
				self.finish();
			}else{	
				Intent intent = new Intent(self, LoginActivity.class);
				startActivity(intent);
				self.finish();
			}			
			
		}		
	}
	
	private class ProgressLoadingTask extends Thread {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();			
			
			try {
				progressBar.setVisibility(View.VISIBLE);
				
				// Sleeping
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				Log.e("Exception", e.getMessage());
			}	
			
			launcher = new IntentLauncher();
			launcher.start();
		}
	}
	
	@Override
	protected void onDestroy() {
		if (launcher != null)
			launcher.interrupt();
		super.onDestroy();
	}
}
