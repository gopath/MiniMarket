package ac.id.itb.d4.minimart.costumer.view;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.costumer.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.costumer.connection.Helper;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import ac.id.itb.d4.minimart.costumer.utils.TransparentProgressDialog;
import ac.id.itb.d4.minimart.costumer.utils.AppConstant;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends SherlockActivity implements ConnectionInterface {

	private LoginActivity self = this;
	private Button btnLogin;
	private TextView tvNewAccount;
	private EditText etUsername, etPassword;
	private TransparentProgressDialog progressDialog;
	private SharedPreferences sessions;
	private AsynConnection connection;	
	private Singleton singleton;
	private String urlLogin;
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_login);
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Login");
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		tvNewAccount = (TextView) findViewById(R.id.tvLoginNewAccount);
		etUsername = (EditText) findViewById(R.id.etLoginUsername);
		etPassword = (EditText) findViewById(R.id.etLoginPassword);
		
		sessions = getSharedPreferences("SESSION", 0);		
		urlLogin = getURLAddress();
		if(!urlLogin.matches("")){
			doLogin();
		}
		
		tvNewAccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(self, RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getSupportMenuInflater();				
		menuInflater.inflate(R.menu.menu_login, menu);
		return super.onCreateOptionsMenu(menu);
	}
		
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_login_settings:
			Intent intent = new Intent(self, SettingURLActivity.class);
			startActivity(intent);
			return true;
		default:
			return false;
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
					
					try {
						JSONObject jObj = new JSONObject(value.toString());
						jObj = jObj.getJSONObject("login");
						String login = jObj.getString("status");
						
						if(login == "1"){
							SharedPreferences.Editor edit = sessions.edit();
							edit.putString("username", etUsername.getText().toString());
							edit.putString("password", etPassword.getText().toString());
							edit.putBoolean("isLogin", true);
							edit.commit();
							
							Intent intent = new Intent(self, MiniMartCostumerMainActivity.class);
							startActivity(intent);
							self.finish();
						} else {
							AppMsg appMsg = AppMsg.makeText(self, "Login Failed", AppMsg.STYLE_ALERT);
							appMsg.show();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
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
		progressDialog.dismiss();
	}

	@Override
	public void callBackOnStart(Object value, int responseCode, int type) {
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
	
	private void doLogin(){
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!etUsername.getText().toString().matches("") && !etPassword.getText().toString().matches("")){																				
					//loginAction(urlLogin, etUsername.getText().toString(), etPassword.getText().toString());																						
					
					SharedPreferences.Editor edit = sessions.edit();
					edit.putString("username", etUsername.getText().toString());
					edit.putString("password", etPassword.getText().toString());
					edit.putBoolean("isLogin", true);
					edit.commit();
					
					Intent intent = new Intent(self, MiniMartCostumerMainActivity.class);
					startActivity(intent);
					self.finish();
				} else {
					AppMsg appMsg = AppMsg.makeText(self, "Please Input Field", AppMsg.STYLE_ALERT);
					appMsg.show();
				}				
			}
		});	
	}
	
	private void loginAction(String url, String username, String password) {
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.progress);
			progressDialog.show();
			
			Log.i("Login URL", url);

			connection = new AsynConnection(self, url, 1, username, password);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("key", "opencrx");
			params.put("txtusername", etUsername.getText().toString());			
			params.put("txtpassword", etPassword.getText().toString());			
			connection.asyncConnectRequest(params, RequestType.POST);
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();			
		}
	}
	
	private String getURLAddress(){		
		singleton.getInstance().setBaseURL(singleton.getInstance().getDefaultPreferences(self, "prefAddress"));
		AppConstant.BASE_URL = singleton.getInstance().getBaseURL();
		urlLogin = "http://" + AppConstant.BASE_URL + AppConstant.URL_LOGIN;
		Log.i("LoginActivity", urlLogin);
		return urlLogin;
	}
}
