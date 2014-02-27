package ac.id.itb.d4.minimart.purchasing.view;

import java.util.Vector;

import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.adapter.PurchasePaymentListAdapter;
import ac.id.itb.d4.minimart.purchasing.connection.AsynConnection;
import ac.id.itb.d4.minimart.purchasing.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.purchasing.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.purchasing.connection.Helper;
import ac.id.itb.d4.minimart.purchasing.model.PurchasePayment;
import ac.id.itb.d4.minimart.purchasing.parser.PurchasePaymentParser;
import ac.id.itb.d4.minimart.purchasing.utils.AppConstant;
import ac.id.itb.d4.minimart.purchasing.utils.Singleton;
import ac.id.itb.d4.minimart.purchasing.utils.TransparentProgressDialog;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.appmsg.AppMsg;

public class PurchasePaymentActivity extends SherlockFragmentActivity implements ConnectionInterface {

	private PurchasePaymentActivity self = this;
//	private ListView listViewActivities;
	private TransparentProgressDialog progressDialog;
	private ActionBar mActionBar;
	private Intent intent;
	private String urlActivities;
		
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activities);
				
		// Activate ActionBar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);	
		mActionBar.setTitle("Activities");
		
		urlActivities = "http://" + Singleton.getInstance().getDefaultPreferences(self, "prefAddress") + ":8080" + AppConstant.URL_ACTIVITY;
		Log.i("ActivitiesFragment", urlActivities);
		//doAccount(urlAccount, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
		
//		listViewActivities = (ListView) findViewById(R.id.listAccount);
	}	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {      
		
        menu.add(Menu.NONE, R.id.action_refresh, Menu.NONE, "")
        	.setIcon(R.drawable.ic_action_refresh)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    
        return super.onCreateOptionsMenu(menu);        
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(self, MiniMartPurchasingMainActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_refresh:
			Toast.makeText(self, "Refresh", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return false;
		}
	}
	
	private void doAccount(String url, String username, String password){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.progress);
			progressDialog.show();
			
			AsynConnection connection = new AsynConnection(this, url, 1, username, password);
			connection.asyncConnectRequest(null, RequestType.GET);			
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();	
			
			setContentView(R.layout.no_connection);
			ImageView tapToRefresh = (ImageView) findViewById(R.id.tapToRefresh);
			tapToRefresh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					doAccount(urlActivities, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
				}
			});
		}
	}
	
	@Override
	public void callBackOnSuccess(final Object value, int responseCode, final int type) {
		// TODO Auto-generated method stub

		progressDialog.dismiss();
		
		self.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				switch (type) {
				case 1:
					Log.e("RESPONSE", value.toString());
					
			        Vector<PurchasePayment> vectorAccount = new Vector<PurchasePayment>();
			        PurchasePaymentParser accountParser = new PurchasePaymentParser();
			        vectorAccount = accountParser.parseData(value.toString());

			        PurchasePaymentListAdapter adapter = new PurchasePaymentListAdapter(self, vectorAccount);
//			        listViewActivities.setAdapter(adapter);
			        break;

				default:
					break;
				}
			}
		});
		
	}

	@Override
	public void callBackOnFailed(Object value, int responseCode, int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callBackOnFinish(Object value, int responseCode, int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callBackOnConnect(Object value, int responseCode, int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callBackOnStart(Object value, int responseCode, int type) {
		// TODO Auto-generated method stub

	}

}
