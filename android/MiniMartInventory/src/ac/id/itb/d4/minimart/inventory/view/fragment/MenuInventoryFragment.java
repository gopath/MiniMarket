package ac.id.itb.d4.minimart.inventory.view.fragment;

import ac.id.itb.d4.minimart.inventory.R;
import ac.id.itb.d4.minimart.inventory.utils.Singleton;
import ac.id.itb.d4.minimart.inventory.view.StockOutActivity;
import ac.id.itb.d4.minimart.inventory.view.ReportStockInActivity;
import ac.id.itb.d4.minimart.inventory.view.LoginActivity;
import ac.id.itb.d4.minimart.inventory.view.MiniMartInventoryMainActivity;
import ac.id.itb.d4.minimart.inventory.view.StockInActivity;
import ac.id.itb.d4.minimart.inventory.view.ProfileActivity;
import ac.id.itb.d4.minimart.inventory.view.SettingURLActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class MenuInventoryFragment extends SherlockFragment implements OnClickListener {
	
	private Context ctx;
	private LayoutInflater inflater;
	
	private TextView tvName, tvPlace, tvSetting, tvLogout, 
		tvHome, tvStockIn, tvStockOut, tvReportIn, tvReportOut;
	private ImageView ivUser, ivCover;
	private SharedPreferences sessions;
	private Intent intent;
	private Bundle bundle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		this.inflater = inflater;
		this.ctx = getActivity();
		this.sessions = ctx.getSharedPreferences("SESSION", 0);		
		this.bundle = savedInstanceState;
		
		View v = inflater.inflate(R.layout.fragment_sliding_menu, container, false);
		
		ivUser = (ImageView) v.findViewById(R.id.ivUser);
		tvName = (TextView) v.findViewById(R.id.tvName);
		tvName.setText(Singleton.getInstance().getStringPreferences(ctx, "username"));
		tvPlace = (TextView) v.findViewById(R.id.tvPlace);
		tvPlace.setVisibility(View.INVISIBLE);
		tvSetting = (TextView) v.findViewById(R.id.tvSetting);
		tvLogout = (TextView) v.findViewById(R.id.tvLogout);
		tvHome = (TextView) v.findViewById(R.id.tvHome);
		tvStockIn = (TextView) v.findViewById(R.id.tvStockIn);
		tvStockOut = (TextView) v.findViewById(R.id.tvStockOut);
		tvReportIn = (TextView) v.findViewById(R.id.tvReportIn);
		tvReportOut = (TextView) v.findViewById(R.id.tvReportOut);
		
		// onclick listener
		ivUser.setOnClickListener(this);
		tvSetting.setOnClickListener(this);
		tvLogout.setOnClickListener(this);		
		tvHome.setOnClickListener(this);
		tvStockIn.setOnClickListener(this);
		tvStockOut.setOnClickListener(this);
		tvReportIn.setOnClickListener(this);
		tvReportOut.setOnClickListener(this);
		
		return v;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.tvSetting:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, SettingURLActivity.class);
			startActivity(intent);			
			break;
		case R.id.tvLogout:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
									
			SharedPreferences.Editor edit = sessions.edit();
			edit.clear();
			edit.commit();			
			
			intent = new Intent(ctx, LoginActivity.class);
			startActivity(intent);			
			getActivity().finish();
			break;
		case R.id.ivUser:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, ProfileActivity.class);
			startActivity(intent);						
			break;
		case R.id.tvHome:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			FrameLayout frame = new FrameLayout(ctx);
			frame.setId(1);
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(1, HomeFragment.newInstance(getActivity())).commit();
			break;
		case R.id.tvStockIn:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, StockOutActivity.class);
			startActivity(intent);
			break;
		case R.id.tvStockOut:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, ReportStockInActivity.class);
			startActivity(intent);
			break;
		case R.id.tvReportIn:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, StockInActivity.class);
			startActivity(intent);
			break;
		case R.id.tvReportOut:
			MiniMartInventoryMainActivity.toggleNavigationMenu();
			Toast.makeText(ctx, "Sales", Toast.LENGTH_SHORT).show();			
			break;
		default:
			break;
		}
	}	

}
