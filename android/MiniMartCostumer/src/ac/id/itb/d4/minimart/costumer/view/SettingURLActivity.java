package ac.id.itb.d4.minimart.costumer.view;

import ac.id.itb.d4.minimart.costumer.R;
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

		addPreferencesFromResource(R.xml.preference);
				
	}
}
