package ac.id.itb.d4.minimart.manager.view;

import java.util.Vector;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.manager.R;
import ac.id.itb.d4.minimart.manager.adapter.DocumentsListAdapter;
import ac.id.itb.d4.minimart.manager.connection.AsynConnection;
import ac.id.itb.d4.minimart.manager.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.manager.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.manager.connection.Helper;
import ac.id.itb.d4.minimart.manager.model.Documents;
import ac.id.itb.d4.minimart.manager.parser.DocumentParser;
import ac.id.itb.d4.minimart.manager.utils.AppConstant;
import ac.id.itb.d4.minimart.manager.utils.Singleton;
import ac.id.itb.d4.minimart.manager.utils.TransparentProgressDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class DocumentActivity extends SherlockActivity implements ConnectionInterface{
	private DocumentActivity self = this;
	private String UrlDocument;
	private TransparentProgressDialog ProgressDialog;
	private ListView listViewDocument;
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_document);

		// Activate ActionBar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Profile");
		
		doDocument();		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(self, MiniMartManagerMainActivity.class);
			startActivity(intent);
			return true;

		default:
			return false;
		}
	}

	
	private void doDocument (){
		UrlDocument = "http://" + Singleton.getInstance().getDefaultPreferences(self, "prefAddress") + ":8080" + AppConstant.URL_DOCUMENT ;
		
		Log.i("DocumentURL", UrlDocument);
		DocumentAction(UrlDocument, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
	}

	private void DocumentAction(String Url, String Username, String Password){
		if (Helper.isOnline(self)){
			
			ProgressDialog = new TransparentProgressDialog(self, R.drawable.progress);
			ProgressDialog.show();
			
			AsynConnection connection = new AsynConnection(self, UrlDocument, 1, Username, Password);
			connection.asyncConnectRequest(null, RequestType.GET);
			
		}else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();		
		}
		
	}
	
	@Override
	public void callBackOnSuccess(final Object value, int responseCode, int type) {
		// TODO Auto-generated method stub
		ProgressDialog.dismiss();
		self.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				DocumentParser parser = new DocumentParser();
				Vector<Documents> data = parser.parseData(value.toString());
				
				listViewDocument = (ListView) findViewById(R.id.listViewDocument);
				DocumentsListAdapter adapterDocument = new DocumentsListAdapter(self, data);
				listViewDocument.setAdapter(adapterDocument);
			}
		});
		
	}

	@Override
	public void callBackOnFailed(Object value, int responseCode, int type) {
		// TODO Auto-generated method stub
		ProgressDialog.dismiss();
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
