package ac.id.itb.d4.minimart.costumer.view;

import java.util.Timer;
import java.util.TimerTask;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.adapter.ProfilePagerAdapter;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ProfileActivity extends SherlockFragmentActivity {

	private ProfileActivity self = this;
	private EditText etUsername, etPassword;
	private Button btnEditProfile;
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	private ProfilePagerAdapter profilePagerAdapter;
	private Intent intent;
	private Timer timer;
	private int index = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		etUsername = (EditText) findViewById(R.id.etProfileUsername);
		etUsername.setEnabled(false);
		etUsername.setText(Singleton.getInstance().getStringPreferences(self, "username"));
		
		etPassword = (EditText) findViewById(R.id.etProfilePassword);
		etPassword.setEnabled(false);
		etPassword.setText(Singleton.getInstance().getStringPreferences(self, "password"));
		
		btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
		btnEditProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(self, EditProfileActivity.class);
				startActivity(intent);
				self.finish();
			}
		});
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Profile");		
		
		// Set View Pager
		setUpViewProfilePager();
		doChangeProfilePager();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(self, HomeTabActivity.class);
			startActivity(intent);
			self.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	
	
	private void setUpViewProfilePager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPagerProfile);
		profilePagerAdapter = new ProfilePagerAdapter(getApplicationContext(),getSupportFragmentManager());
		mViewPager.setAdapter(profilePagerAdapter);
		//mViewPager.setCurrentItem(index);
	}	
	
	private void doChangeProfilePager(){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(index == 0){
					index++;
					mViewPager.setCurrentItem(index);
					if(index == 1){		
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								index = 0;
								mViewPager.setCurrentItem(index);
								doChangeProfilePager();
							}
						}, 5000);										
					}				
				}
				else if(index == 1){
					index--;
					mViewPager.setCurrentItem(index);
					if(index == 0){		
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								index = 1;
								mViewPager.setCurrentItem(index);
								doChangeProfilePager();
							}
						}, 5000);										
					}	
				}
			}
		}, 5000);
	}
}
