package ac.id.itb.d4.minimart.inventory.view.fragment;

import ac.id.itb.d4.minimart.inventory.R;
import ac.id.itb.d4.minimart.inventory.utils.Singleton;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

	private SharedPreferences sessions;
	private static Context ctx;
	private TextView tvName, tvPlace;
	
	public static Fragment newInstance(Context context) {
		ProfileFragment f = new ProfileFragment();
		ctx = context;
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_header, null);	
		
		this.sessions = ctx.getSharedPreferences("SESSION", 0);
		tvName = (TextView) root.findViewById(R.id.tvName);
		tvPlace = (TextView) root.findViewById(R.id.tvPlace);

		tvName.setText(Singleton.getInstance().getStringPreferences(ctx, "username"));
		tvPlace.setVisibility(View.INVISIBLE);
		
		return root;
	}
}
