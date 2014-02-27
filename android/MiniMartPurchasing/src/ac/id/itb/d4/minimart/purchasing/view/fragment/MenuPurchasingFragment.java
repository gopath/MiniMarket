package ac.id.itb.d4.minimart.purchasing.view.fragment;

import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.utils.Singleton;
import ac.id.itb.d4.minimart.purchasing.view.PurchaseOrderActivity;
import ac.id.itb.d4.minimart.purchasing.view.PurchasePaymentActivity;
import ac.id.itb.d4.minimart.purchasing.view.LoginActivity;
import ac.id.itb.d4.minimart.purchasing.view.MiniMartPurchasingMainActivity;
import ac.id.itb.d4.minimart.purchasing.view.GoodReceiveActivity;
import ac.id.itb.d4.minimart.purchasing.view.ProfileActivity;
import ac.id.itb.d4.minimart.purchasing.view.SettingURLActivity;
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

public class MenuPurchasingFragment extends SherlockFragment implements OnClickListener {
	
	private Context ctx;
	private LayoutInflater inflater;
	
	private TextView tvName, tvPlace, tvSetting, tvLogout, 
		tvHome, tvPurchaseOrder, tvPurchasePayment, 
		tvGoodReceive, tvReport;
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
		tvPurchaseOrder = (TextView) v.findViewById(R.id.tvPurchaseOrder);
		tvPurchasePayment = (TextView) v.findViewById(R.id.tvPurchasePayment);
		tvGoodReceive = (TextView) v.findViewById(R.id.tvGoodReceive);
		tvReport = (TextView) v.findViewById(R.id.tvReportPurhasing);
		
		// onclick listener
		ivUser.setOnClickListener(this);
		tvSetting.setOnClickListener(this);
		tvLogout.setOnClickListener(this);		
		tvHome.setOnClickListener(this);
		tvPurchaseOrder.setOnClickListener(this);
		tvPurchasePayment.setOnClickListener(this);
		tvGoodReceive.setOnClickListener(this);
		tvReport.setOnClickListener(this);
		
		return v;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.tvSetting:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, SettingURLActivity.class);
			startActivity(intent);			
			break;
		case R.id.tvLogout:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
									
			SharedPreferences.Editor edit = sessions.edit();
			edit.clear();
			edit.commit();			
			
			intent = new Intent(ctx, LoginActivity.class);
			startActivity(intent);			
			getActivity().finish();
			break;
		case R.id.ivUser:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, ProfileActivity.class);
			startActivity(intent);						
			break;
		case R.id.tvHome:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			FrameLayout frame = new FrameLayout(ctx);
			frame.setId(1);
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(1, HomeFragment.newInstance(getActivity())).commit();
			break;
		case R.id.tvPurchaseOrder:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, PurchaseOrderActivity.class);
			startActivity(intent);
			break;
		case R.id.tvPurchasePayment:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, PurchasePaymentActivity.class);
			startActivity(intent);
			break;
		case R.id.tvGoodReceive:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			intent = new Intent(ctx, GoodReceiveActivity.class);
			startActivity(intent);
			break;
		case R.id.tvReportPurhasing:
			MiniMartPurchasingMainActivity.toggleNavigationMenu();
			Toast.makeText(ctx, "Report", Toast.LENGTH_SHORT).show();			
			break;
		default:
			break;
		}
	}	

}
