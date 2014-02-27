package ac.id.itb.d4.minimart.costumer.view;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.adapter.HomePagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class HomeTabActivity extends SherlockFragmentActivity {

	private HomeTabActivity self = this;
	private ActionBar mActionBar;
	private Tab mTab;
	private static ViewPager mPager;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_tab);
			
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		// Locate ViewPager in .xml
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOffscreenPageLimit(3);
		
		// Activate Fragment Manager
		FragmentManager fm = getSupportFragmentManager();
		
		// Capture ViewPager page swipes
		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
				public void onPageSelected(int position) {
					super.onPageSelected(position);
					
					// Find the ViewPager Position
					mActionBar.setSelectedNavigationItem(position);
			}
		};
		
		// Set view pager adapter
		mPager.setOnPageChangeListener(ViewPagerListener);
		// Locate the adapter class
		HomePagerAdapter homepagerAdapter = new HomePagerAdapter(fm, self);
		// Set the View Pager Adapter into ViewPager
		mPager.setAdapter(homepagerAdapter);
		//mPager.setCurrentItem(1);
		
		// Capture tab button clicks
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// Pass the position on tab click to ViewPager
				mPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}
		};
		
		mTab = mActionBar.newTab().setIcon(R.drawable.menu_profile)
				.setTabListener(tabListener);
		mActionBar.addTab(mTab);

		mTab = mActionBar.newTab().setIcon(R.drawable.home)
				.setTabListener(tabListener);
		mActionBar.addTab(mTab);

		mTab = mActionBar.newTab().setIcon(R.drawable.menu_product)
				.setTabListener(tabListener);
		mActionBar.addTab(mTab);
		
		setCurrentItemPager(1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub		
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_home, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.action_about:
			Intent intent = new Intent(self, AboutActivity.class);
			startActivity(intent);			
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public static void setCurrentItemPager(int position) {
		mPager.setCurrentItem(position);
	}
	
}
