package ac.id.itb.d4.minimart.employee.view.fragment;

import ac.id.itb.d4.minimart.employee.R;
import ac.id.itb.d4.minimart.employee.utils.Singleton;
import ac.id.itb.d4.minimart.employee.view.AccountActivity;
import ac.id.itb.d4.minimart.employee.view.ActivitiesActivity;
import ac.id.itb.d4.minimart.employee.view.LoginActivity;
import ac.id.itb.d4.minimart.employee.view.MiniMartEmployeeMainActivity;
import ac.id.itb.d4.minimart.employee.view.ProductActivity;
import ac.id.itb.d4.minimart.employee.view.ProfileActivity;
import ac.id.itb.d4.minimart.employee.view.SettingURLActivity;
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

public class MenuStandardFragment extends SherlockFragment implements OnClickListener {
	
	private Context ctx;
	private LayoutInflater inflater;
	
	private TextView tvName, tvPlace, tvSetting, tvLogout, 
		tvHome, tvAccount, tvActivity, tvSales, tvProduct, tvDepot, tvDocument;
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
		tvAccount = (TextView) v.findViewById(R.id.tvAccount);
		tvActivity = (TextView) v.findViewById(R.id.tvActivity);
		tvProduct = (TextView) v.findViewById(R.id.tvProduct);
		tvSales = (TextView) v.findViewById(R.id.tvSales);
		tvDepot = (TextView) v.findViewById(R.id.tvDepot);
		tvDocument = (TextView) v.findViewById(R.id.tvDocument);
		
		// onclick listener
		ivUser.setOnClickListener(this);
		tvSetting.setOnClickListener(this);
		tvLogout.setOnClickListener(this);		
		tvHome.setOnClickListener(this);
		tvAccount.setOnClickListener(this);
		tvActivity.setOnClickListener(this);
		tvProduct.setOnClickListener(this);
		tvSales.setOnClickListener(this);
		tvDepot.setOnClickListener(this);
		tvDocument.setOnClickListener(this);
		
		return v;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.tvSetting:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, SettingURLActivity.class);
			startActivity(intent);			
			break;
		case R.id.tvLogout:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
									
			SharedPreferences.Editor edit = sessions.edit();
			edit.clear();
			edit.commit();			
			
			intent = new Intent(ctx, LoginActivity.class);
			startActivity(intent);			
			getActivity().finish();
			break;
		case R.id.ivUser:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, ProfileActivity.class);
			startActivity(intent);						
			break;
		case R.id.tvHome:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			FrameLayout frame = new FrameLayout(ctx);
			frame.setId(1);
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(1, HomeFragment.newInstance(getActivity())).commit();
			break;
		case R.id.tvAccount:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, AccountActivity.class);
			startActivity(intent);
			break;
		case R.id.tvActivity:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, ActivitiesActivity.class);
			startActivity(intent);
			break;
		case R.id.tvProduct:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, ProductActivity.class);
			startActivity(intent);
			break;
		case R.id.tvSales:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			Toast.makeText(ctx, "Sales", Toast.LENGTH_SHORT).show();			
			break;
		case R.id.tvDepot:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			Toast.makeText(ctx, "Depot", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tvDocument:
			MiniMartEmployeeMainActivity.toggleNavigationMenu();
			Toast.makeText(ctx, "Document", Toast.LENGTH_SHORT).show();
			break;		
		default:
			break;
		}
	}	

}
