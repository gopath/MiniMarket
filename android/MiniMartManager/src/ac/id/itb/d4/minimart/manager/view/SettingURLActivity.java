package ac.id.itb.d4.minimart.manager.view;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingURLActivity extends PreferenceActivity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		extracted();
				
	}

	@SuppressWarnings("deprecation")
	private void extracted() {
		addPreferencesFromResource(ac.id.itb.d4.minimart.manager.R.xml.preference);
	}
}
