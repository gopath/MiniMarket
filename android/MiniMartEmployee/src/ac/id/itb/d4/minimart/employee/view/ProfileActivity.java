package ac.id.itb.d4.minimart.employee.view;

import java.util.Timer;
import java.util.TimerTask;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import ac.id.itb.d4.minimart.employee.R;
import ac.id.itb.d4.minimart.employee.adapter.ProfilePagerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends SherlockFragmentActivity {

	private ProfileActivity self = this;
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

		// Activate ActionBar
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
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(self, MiniMartEmployeeMainActivity.class);
			startActivity(intent);
			return true;

		default:
			return false;
		}
	}
	
	private void setUpViewProfilePager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		profilePagerAdapter = new ProfilePagerAdapter(getApplicationContext(),getSupportFragmentManager());
		mViewPager.setAdapter(profilePagerAdapter);
		//mViewPager.setCurrentItem(index);
	}	
	
	public void doChangeProfilePager(){
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
