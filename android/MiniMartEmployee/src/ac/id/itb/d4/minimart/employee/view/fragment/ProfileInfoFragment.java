package ac.id.itb.d4.minimart.employee.view.fragment;

import ac.id.itb.d4.minimart.employee.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileInfoFragment extends Fragment {

	private SharedPreferences sessions;
	private static Context ctx;
	private TextView tvJobs, tvDetails;
	
	public static Fragment newInstance(Context context) {
		ProfileInfoFragment f = new ProfileInfoFragment();	
		ctx = context;
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_info, null);	
		
		this.sessions = ctx.getSharedPreferences("SESSION", 0);
		tvJobs = (TextView) root.findViewById(R.id.tvJob);
		tvDetails = (TextView) root.findViewById(R.id.tvDetails);

		tvJobs.setText("Costumer");
		tvDetails.setVisibility(View.INVISIBLE);
		
		return root;
	}
}
