package ac.id.itb.d4.minimart.costumer.view;

import ac.id.itb.d4.minimart.costumer.R;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class LoginActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_login);
	}
}
