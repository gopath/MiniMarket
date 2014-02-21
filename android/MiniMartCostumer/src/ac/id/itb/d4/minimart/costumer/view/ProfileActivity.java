package ac.id.itb.d4.minimart.costumer.view;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;

public class ProfileActivity extends SherlockActivity {

	private ProfileActivity self = this;
	private SharedPreferences sessions;	
	private EditText etUsername, etPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		sessions = getSharedPreferences("SESSION", 0);		
		etUsername = (EditText) findViewById(R.id.etProfileUsername);
		etUsername.setEnabled(false);
		etUsername.setText(Singleton.getInstance().getStringPreferences(self, "username"));
		
		etPassword = (EditText) findViewById(R.id.etProfilePassword);
		etPassword.setEnabled(false);
		etPassword.setText(Singleton.getInstance().getStringPreferences(self, "password"));
		
	}
}
