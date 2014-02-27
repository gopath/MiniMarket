package ac.id.itb.d4.minimart.costumer.view.fragment;

import ac.id.itb.d4.minimart.costumer.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileDetailsFragment extends Fragment {

	private SharedPreferences sessions;
	private static Context ctx;
	private TextView tvPlace, tvAddress, tvBirthdate;
	
	public static Fragment newInstance(Context context) {
		ProfileDetailsFragment f = new ProfileDetailsFragment();	
		ctx = context;
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_details, null);	
		
		this.sessions = ctx.getSharedPreferences("SESSION", 0);
		tvPlace = (TextView) root.findViewById(R.id.tvProfilePlace);
		tvAddress = (TextView) root.findViewById(R.id.tvProfileAddress);
		tvBirthdate = (TextView) root.findViewById(R.id.tvProfileBirthdate);
		
		return root;
	}
}
