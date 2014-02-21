package ac.id.itb.d4.minimart.costumer.view;

import java.util.Calendar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection;
import ac.id.itb.d4.minimart.costumer.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.costumer.connection.Helper;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.costumer.utils.TransparentProgressDialog;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends SherlockActivity implements ConnectionInterface {

	private RegisterActivity self = this;
	private Button btnRegister, btnDatePicker;
	private EditText etFullName;
	private String urlRegister;
	private TransparentProgressDialog progressDialog;
	private ActionBar mActionBar;
	private final int DATE_DIALOG_ID = 1;
	private int year, month, day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_register);
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Register");
		
		btnRegister = (Button) findViewById(R.id.btnRegisterUser);
		btnDatePicker = (Button) findViewById(R.id.btnRegisterDatePicker);
				
		btnDatePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// get current date
				final Calendar c = Calendar.getInstance();
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doRegister();
			}
		});
		
	}
	
	private void doRegister(){
		
//		if(){
//			registerAction(urlRegister, etUsername.getText().toString(), etPassword.getText().toString());
//		} else {
//			AppMsg appMsg = AppMsg.makeText(self, "Please Input Field", AppMsg.STYLE_ALERT);
//			appMsg.show();
//		}
	}
	
	private void registerAction(String url, String username, String password){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.progress);
			progressDialog.show();
			
			Log.i("Login URL", url);

			AsynConnection connection = new AsynConnection(self, url, 1, username, password);
			connection.asyncConnectRequest(null, RequestType.GET);
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();			
		}
	}

	@Override
	public void callBackOnSuccess(Object value, int responseCode, int type) {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
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
}
