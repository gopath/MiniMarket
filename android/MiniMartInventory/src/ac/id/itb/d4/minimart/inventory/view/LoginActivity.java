package ac.id.itb.d4.minimart.inventory.view;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.inventory.R;
import ac.id.itb.d4.minimart.inventory.connection.AsynConnection;
import ac.id.itb.d4.minimart.inventory.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.inventory.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.inventory.connection.Helper;
import ac.id.itb.d4.minimart.inventory.utils.AppConstant;
import ac.id.itb.d4.minimart.inventory.utils.Singleton;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends SherlockActivity implements ConnectionInterface {

	private final LoginActivity self = this;
	private EditText usernameET, passwordET;
	private Button btnLogin;
	private ProgressDialog progressDialog;
	private SharedPreferences sessions;
	private AsynConnection connection;	
	private Singleton singleton;
	private String urlLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		sessions = getSharedPreferences("SESSION", 0);
		urlLogin = getURLAddress();
		if(!urlLogin.matches("")){
			doLogin();
		}		
						
	}	
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = self.getSupportMenuInflater();				
        menuInflater.inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_login_settings:
			Intent intent = new Intent(self, SettingURLActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void callBackOnSuccess(final Object value, final int responseCode, int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		switch(type){
		case 1:
			self.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
						
					SharedPreferences.Editor edit = sessions.edit();
					edit.putString("username", usernameET.getText().toString());
					edit.putString("password", passwordET.getText().toString());
					edit.putBoolean("isLogin", true);
					edit.commit();
						
					Intent intent = new Intent(self, MiniMartInventoryMainActivity.class);
					startActivity(intent);
					self.finish();
					
				}
			});
			break;
		}
	}

	@Override
	public void callBackOnFailed(Object value, final int responseCode, int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		//Log.e("LoginActivity", String.valueOf(responseCode) + " : " + value.toString());
		AppMsg appMsg = AppMsg.makeText(self, "Login Failed", AppMsg.STYLE_ALERT);
		appMsg.show();
	}

	@Override
	public void callBackOnFinish(Object value, final int responseCode, int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callBackOnConnect(Object value, final int responseCode, int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callBackOnStart(Object value, final int responseCode, int type) {
		// TODO Auto-generated method stub
		
	}
	
	private void loginAction(String url, String username, String password) {
		if (Helper.isOnline(self)) {
			progressDialog = new ProgressDialog(self);
			progressDialog.setMessage("Signing In..");
			progressDialog.show();
			
			Log.i("Login URL", url);

			connection = new AsynConnection(self, url, 1, username, password);
			connection.asyncConnectRequest(null, RequestType.GET);
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();			
		}
	}
	
	private void doLogin(){
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!usernameET.getText().toString().matches("") && !passwordET.getText().toString().matches("")){										
					loginAction(urlLogin, usernameET.getText().toString(), passwordET.getText().toString());
				} else {
					AppMsg appMsg = AppMsg.makeText(self, "Please Input Field", AppMsg.STYLE_ALERT);
					appMsg.show();
				}				
			}
		});	
	}
		
	private String getURLAddress(){		
		singleton.getInstance().setBaseURL(singleton.getInstance().getDefaultPreferences(self, "prefAddress"));
		AppConstant.BASE_URL = singleton.getInstance().getBaseURL();
		urlLogin = "http://" + AppConstant.BASE_URL + ":8080" + AppConstant.URL_LOGIN;
		Log.i("LoginActivity", urlLogin);
		return urlLogin;
	}
}
