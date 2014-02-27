package ac.id.itb.d4.minimart.costumer.view;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class EditProfileActivity extends SherlockActivity {

	private EditProfileActivity self = this;
	private EditText etUsername, etPassword;
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		etUsername = (EditText) findViewById(R.id.etEditProfileUsername);
		etUsername.setText(Singleton.getInstance().getStringPreferences(self, "username"));
		
		etPassword = (EditText) findViewById(R.id.etEditProfilePassword);
		etPassword.setText(Singleton.getInstance().getStringPreferences(self, "password"));
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Edit Profile");		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(self, ProfileActivity.class);
			startActivity(intent);
			self.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	
}
