package ac.id.itb.d4.minimart.purchasing.view;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.R.layout;
import ac.id.itb.d4.minimart.purchasing.R.menu;
import ac.id.itb.d4.minimart.purchasing.view.fragment.HomeFragment;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class MiniMartPurchasingMainActivity extends SherlockFragmentActivity {

	private MiniMartPurchasingMainActivity self = this;
	private static SlidingMenu mSlidingMenu;
	private ActionBar mActionBar;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		Log.i("Width Size", String.valueOf(width));
				
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("");
				
		// Activate Sliding Menu
		mSlidingMenu = new SlidingMenu(self);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		mSlidingMenu.setShadowWidthRes(R.dimen.dimens_25dp);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setFadeDegree(0.45f);
		mSlidingMenu.attachToActivity(self, SlidingMenu.SLIDING_WINDOW);
		mSlidingMenu.setMenu(R.layout.sliding_menu);
		if(width > 1000){
			mSlidingMenu.setBehindOffsetRes(R.dimen.dimens_800dp);			
		} else if (width > 600){
			mSlidingMenu.setBehindOffsetRes(R.dimen.dimens_400dp);			
		} else {
			mSlidingMenu.setBehindOffsetRes(R.dimen.dimens_100dp);
		}
				
		FrameLayout frame = new FrameLayout(this);
		frame.setId(1);
		setContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		if (savedInstanceState == null) {
			setInitialFragment();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mSlidingMenu.toggle();
			return true;

		default:
			return false;
		}
	}

	public static void toggleNavigationMenu(){
		mSlidingMenu.toggle();
	}
	
	private void setInitialFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(1, HomeFragment.newInstance(self)).commit();
	}
}
