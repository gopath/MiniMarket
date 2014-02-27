package ac.id.itb.d4.minimart.manager.view;

import java.util.Timer;
import java.util.TimerTask;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.ParserAdapter;

import ac.id.itb.d4.minimart.manager.R;
import ac.id.itb.d4.minimart.manager.view.fragment.HomeFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ProfileActivity extends SherlockFragmentActivity {

	private ProfileActivity self = this;
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	private Object profilePagerAdapter;
	private Intent intent;
	private Timer timer;
	private int index = 0;
	private TextView tvName, tvJobs, tvAddress;
	private HomeFragment home;
	
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
		
		tvName = (TextView) findViewById(R.id.tvProfileValueFullName);
		tvJobs = (TextView) findViewById(R.id.tvProfileValueJobs);
		tvAddress = (TextView) findViewById(R.id.tvProfileValueAddress);
						
		tvName.setText(home.accountUser.getFullName());
		tvJobs.setText(home.accountUser.getJobRole());
		tvAddress.setText(home.accountUser.getDescription());
		
		// Set View Pager
		try {
			setUpViewProfilePager();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doChangeProfilePager();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(self, MiniMartManagerMainActivity.class);
			startActivity(intent);
			return true;

		default:
			return false;
		}
	}
	
	private void setUpViewProfilePager() throws SAXException {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		profilePagerAdapter = new ParserAdapter();
		mViewPager.setAdapter((PagerAdapter) profilePagerAdapter);
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
