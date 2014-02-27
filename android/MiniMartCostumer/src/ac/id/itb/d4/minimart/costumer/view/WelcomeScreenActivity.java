package ac.id.itb.d4.minimart.costumer.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import ac.id.itb.d4.minimart.costumer.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreenActivity extends SherlockActivity{

	private WelcomeScreenActivity self = this;
	private Button btnLogin, btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_welcome);
		
		btnLogin = (Button) findViewById(R.id.btnCheckLogin);
		btnRegister = (Button) findViewById(R.id.btnCheckRegister);
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(self, LoginActivity.class);
				startActivity(intent);
				self.finish();
			}
		});
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(self, RegisterActivity.class);
				startActivity(intent);
				self.finish();
			}
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getSupportMenuInflater();				
		menuInflater.inflate(R.menu.menu_login, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_login_settings:
			Intent intent = new Intent(self, SettingURLActivity.class);
			startActivity(intent);
			return true;
		default:
			return false;
		}		
	}
	
}
