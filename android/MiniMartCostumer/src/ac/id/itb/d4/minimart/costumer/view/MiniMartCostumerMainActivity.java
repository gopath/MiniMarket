package ac.id.itb.d4.minimart.costumer.view;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import ac.id.itb.d4.minimart.costumer.utils.TransparentProgressDialog;
import ac.id.itb.d4.minimart.costumer.utils.sqlite.SqliteCopyDatabase;
import ac.id.itb.d4.minimart.costumer.utils.sqlite.SqliteDatabaseHelper;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MiniMartCostumerMainActivity extends SherlockFragmentActivity {

	private MiniMartCostumerMainActivity self = this;
	private Button btnMenuProduct, btnMenuCart, btnMenuProfile, btnMenuLogout;
	private TransparentProgressDialog progressDialog;
	private SharedPreferences sessions;
	private Intent intent;
	private ActionBar mActionBar;
	private SqliteCopyDatabase sqliteCopy;
	private SqliteDatabaseHelper sqliteHelper;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	
		this.sessions = getSharedPreferences("SESSION", 0);		
		sqliteCopy = new SqliteCopyDatabase(self);
		sqliteHelper = new SqliteDatabaseHelper(self);
		
		btnMenuProduct = (Button) findViewById(R.id.homeMenuProduct);
		btnMenuCart = (Button) findViewById(R.id.homeMenuCart);
		btnMenuProfile = (Button) findViewById(R.id.homeMenuProfile);
		btnMenuLogout = (Button) findViewById(R.id.homeMenuLogout);
		
		btnMenuProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(self, GoodsActivity.class);
				startActivity(intent);
			}
		});
		
		btnMenuCart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(Singleton.getInstance().getBooleanPreferences(self, "isTransaction") == true){
					intent = new Intent(self, CartActivity.class);
					startActivity(intent);
				} else {
					AppMsg appMsg = AppMsg.makeText(self, "Please start transaction first", AppMsg.STYLE_ALERT);
					appMsg.show();
				}
			}
		});

		btnMenuProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(self, ProfileActivity.class);
				startActivity(intent);
			}
		});
		
		btnMenuLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog = new TransparentProgressDialog(self, R.drawable.progress);
				progressDialog.show();
				
				SharedPreferences.Editor edit = sessions.edit();
				edit.clear();
				edit.commit();
				
				sqliteHelper.deleteTransactionData(1);
				
				progressDialog.dismiss();
				intent = new Intent(self, CheckLoginActivity.class);
				startActivity(intent);
				self.finish();
			}
		});
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("");
				
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}			
	
}
