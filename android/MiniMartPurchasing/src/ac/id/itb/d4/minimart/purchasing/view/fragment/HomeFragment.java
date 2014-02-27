package ac.id.itb.d4.minimart.purchasing.view.fragment;

import java.util.Vector;

import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.connection.AsynConnection;
import ac.id.itb.d4.minimart.purchasing.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.purchasing.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.purchasing.connection.Helper;
import ac.id.itb.d4.minimart.purchasing.model.PurchasePayment;
import ac.id.itb.d4.minimart.purchasing.parser.PurchasePaymentParser;
import ac.id.itb.d4.minimart.purchasing.utils.AppConstant;
import ac.id.itb.d4.minimart.purchasing.utils.Singleton;
import ac.id.itb.d4.minimart.purchasing.utils.TransparentProgressDialog;
import ac.id.itb.d4.minimart.purchasing.view.ProfileActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.devspark.appmsg.AppMsg;

public class HomeFragment extends SherlockFragment implements ConnectionInterface {

	private static Activity self;
	private SharedPreferences sessions;
	private View view;
	private LayoutInflater inflater;
	private ViewGroup viewGroup;
	private String urlAccount, urlUserHome;
	private TransparentProgressDialog progressDialog;
	private int index = 0;
	private Button btnHomeProfile;
	    
	public static Fragment newInstance(Activity activity) {
		self = activity;
		HomeFragment f = new HomeFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
			
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {								
		
		this.inflater = inflater;
		this.viewGroup = container;
		this.sessions = self.getSharedPreferences("SESSION", 0);	
		
		urlAccount = "http://" + Singleton.getInstance().getDefaultPreferences(self, "prefAddress") + ":8080" + AppConstant.URL_ACCOUNT;
		Log.i("HomeFragment", urlAccount);
		doAccount(urlAccount, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));				
						
		view = inflater.inflate(R.layout.activity_home, container, false);
		btnHomeProfile = (Button) view.findViewById(R.id.btnHomeProfile);
		btnHomeProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(self, ProfileActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}

	@Override
	public void callBackOnSuccess(final Object value, final int responseCode, final int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		
		self.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				switch (type) {
				case 1:
					Log.v("RESPONSE ACCOUNT", value.toString());
					
			        Vector<PurchasePayment> vectorAccount = new Vector<PurchasePayment>();
			        PurchasePaymentParser accountParser = new PurchasePaymentParser();
			        vectorAccount = accountParser.parseData(value.toString());
			        String lastName = "";
			        
			        // find account href that equal with username
			        for (int i = 0; i < vectorAccount.size(); i++) {
						lastName = vectorAccount.get(i).getLastName().toLowerCase();
						Log.v("HomeFragment", lastName);
						if(lastName.contains(Singleton.getInstance().getStringPreferences(self, "password").toLowerCase())){
							index = i;
							Log.v("HomeFragment", String.valueOf(index));
							break;
						}
					}			        			       
			        
			        urlUserHome = vectorAccount.get(index).getHref();
			        Log.v("HomeFragment", urlUserHome);
					doUserHome(urlUserHome, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
			        
			        break;
				case 2:
					Log.v("RESPONSE USERHOME", value.toString());
					
			        vectorAccount = new Vector<PurchasePayment>();
			        accountParser = new PurchasePaymentParser();
			        vectorAccount = accountParser.parseData(value.toString());
					
			        Log.i("VCARD", vectorAccount.get(0).getVcard());
			        
			        SharedPreferences.Editor edit = sessions.edit();
					edit.putString("vcard", vectorAccount.get(0).getVcard());
					edit.commit();
			        
					break;
				default:
					break;
				}
			}
		});
		
	}

	@Override
	public void callBackOnFailed(final Object value, final int responseCode, final int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();		
		
		self.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				AppMsg appMsg = AppMsg.makeText(self, "Error Getting Data", AppMsg.STYLE_ALERT);
				appMsg.show();	

			}
		});
		
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
	
	private void doAccount(final String url, String username, String password){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(getActivity(), R.drawable.progress);
			progressDialog.show();
			
			AsynConnection connection = new AsynConnection(this, url, 1, username, password);
			connection.asyncConnectRequest(null, RequestType.GET);			
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();		
			
			view = inflater.inflate(R.layout.no_connection, viewGroup);
			ImageView tapToRefresh = (ImageView) view.findViewById(R.id.tapToRefresh);
			tapToRefresh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					doAccount(urlAccount, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
				}
			});
		}
	}
	
	private void doUserHome(final String url, String username, String password){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(getActivity(), R.drawable.progress);
			progressDialog.show();
			
			AsynConnection connection = new AsynConnection(this, url, 2, username, password);
			connection.asyncConnectRequest(null, RequestType.GET);			
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();		
			
			view = inflater.inflate(R.layout.no_connection, viewGroup);
			ImageView tapToRefresh = (ImageView) view.findViewById(R.id.tapToRefresh);
			tapToRefresh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					doUserHome(urlUserHome, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
				}
			});
		}
	}
	
}
