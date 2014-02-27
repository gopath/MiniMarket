package ac.id.itb.d4.minimart.costumer.view.fragment;

import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import ac.id.itb.d4.minimart.costumer.utils.TransparentProgressDialog;
import ac.id.itb.d4.minimart.costumer.utils.sqlite.SqliteCopyDatabase;
import ac.id.itb.d4.minimart.costumer.utils.sqlite.SqliteDatabaseHelper;
import ac.id.itb.d4.minimart.costumer.view.AboutActivity;
import ac.id.itb.d4.minimart.costumer.view.CartActivity;
import ac.id.itb.d4.minimart.costumer.view.GoodsActivity;
import ac.id.itb.d4.minimart.costumer.view.ProfileActivity;
import ac.id.itb.d4.minimart.costumer.view.WelcomeScreenActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	private static Context ctx;
	private Button btnMenuProduct, btnMenuCart, btnMenuProfile, btnMenuLogout;
	private TransparentProgressDialog progressDialog;
	private SharedPreferences sessions;
	private Intent intent;
	private SqliteCopyDatabase sqliteCopy;
	private SqliteDatabaseHelper sqliteHelper;
	
	public static Fragment newInstance(Context context) {
		HomeFragment f = new HomeFragment();
		ctx = context;
		return f;
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);	
				
		this.sessions = ctx.getSharedPreferences("SESSION", 0);		
		sqliteCopy = new SqliteCopyDatabase(ctx);
		sqliteHelper = new SqliteDatabaseHelper(ctx);
		
		btnMenuProduct = (Button) root.findViewById(R.id.homeMenuProduct);
		btnMenuCart = (Button) root.findViewById(R.id.homeMenuCart);
		btnMenuProfile = (Button) root.findViewById(R.id.homeMenuProfile);
		btnMenuLogout = (Button) root.findViewById(R.id.homeMenuLogout);				
		
		btnMenuProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(ctx, GoodsActivity.class);
				startActivity(intent);
			}
		});
		
		btnMenuCart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(Singleton.getInstance().getBooleanPreferences(ctx, "isTransaction") == true){
					intent = new Intent(ctx, CartActivity.class);
					startActivity(intent);
				} else {
					AppMsg appMsg = AppMsg.makeText(getActivity(), "Please start transaction first", AppMsg.STYLE_ALERT);
					appMsg.show();
				}
			}
		});
		
		btnMenuProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(ctx, ProfileActivity.class);
				startActivity(intent);
			}
		});
		
		btnMenuLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog = new TransparentProgressDialog(ctx, R.drawable.progress);
				progressDialog.show();
				
				SharedPreferences.Editor edit = sessions.edit();
				edit.clear();
				edit.commit();
				
				sqliteHelper.deleteTransactionData(1);
				
				progressDialog.dismiss();
				intent = new Intent(ctx, WelcomeScreenActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		
		return root;
	}	
		
}
