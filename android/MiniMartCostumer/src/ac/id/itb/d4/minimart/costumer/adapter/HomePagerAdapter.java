package ac.id.itb.d4.minimart.costumer.adapter;

import ac.id.itb.d4.minimart.costumer.view.fragment.HomeFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	final int PAGE_COUNT = 3;
	private Context context;
	
	public HomePagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		this.context = ctx;		
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {

		case 0:
			Fragment homeFragment1 = HomeFragment.newInstance(context);
			return homeFragment1;

		case 1:
			Fragment homeFragment2 = HomeFragment.newInstance(context);
			return homeFragment2;

		case 2:
			Fragment homeFragment3 = HomeFragment.newInstance(context);
			return homeFragment3;

		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

}
