package ac.id.itb.d4.minimart.purchasing.view;

import java.util.Vector;

import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.adapter.GoodReceiveListAdapter;
import ac.id.itb.d4.minimart.purchasing.connection.AsynConnection;
import ac.id.itb.d4.minimart.purchasing.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.purchasing.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.purchasing.connection.Helper;
import ac.id.itb.d4.minimart.purchasing.connection.LoadMoreDataTask;
import ac.id.itb.d4.minimart.purchasing.connection.PullAndLoadInterface;
import ac.id.itb.d4.minimart.purchasing.connection.PullToRefreshDataTask;
import ac.id.itb.d4.minimart.purchasing.model.GoodReceive;
import ac.id.itb.d4.minimart.purchasing.parser.GoodReceiveParser;
import ac.id.itb.d4.minimart.purchasing.utils.AppConstant;
import ac.id.itb.d4.minimart.purchasing.utils.Singleton;
import ac.id.itb.d4.minimart.purchasing.utils.TransparentProgressDialog;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullAndLoadListView.OnLoadMoreListener;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.devspark.appmsg.AppMsg;

public class GoodReceiveActivity extends SherlockListActivity 
	implements ConnectionInterface, PullAndLoadInterface {

	private GoodReceiveActivity self = this;
	private TransparentProgressDialog progressDialog;
	private ActionBar mActionBar;
	private Intent intent;
	private String urlProduct;
		
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
				
		// Activate ActionBar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Product");				
		
		urlProduct = "http://" + Singleton.getInstance().getDefaultPreferences(self, "prefAddress") + ":8080" + AppConstant.URL_PRODUCT;
		Log.i("ProductFragment", urlProduct);
		doProduct(urlProduct, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
		
	}	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {      
		
        menu.add(Menu.NONE, R.id.action_new, Menu.NONE, "")
            .setIcon(R.drawable.ic_action_new)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
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
		case R.id.action_new:
			Toast.makeText(self, "New", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_refresh:
			doProduct(urlProduct, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
			return true;
		default:
			return false;
		}
	}
	
	private void doProduct(String url, String username, String password){
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
					doProduct(urlProduct, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
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
					
			        Vector<GoodReceive> vectorProduct = new Vector<GoodReceive>();
			        GoodReceiveParser productParser = new GoodReceiveParser();
			        vectorProduct = productParser.parseData(value.toString());

			        GoodReceiveListAdapter adapter = new GoodReceiveListAdapter(self, vectorProduct);
			        setListAdapter(adapter);
			        
			        // Set a listener to be invoked when the list should be refreshed.
					((PullAndLoadListView) getListView()).setOnRefreshListener(new OnRefreshListener() {

						public void onRefresh() {
							// Do work to refresh the list here.
							new PullToRefreshDataTask(self).execute();
						}
					});
					
					// set a listener to be invoked when the list reaches the end
					((PullAndLoadListView) getListView()).setOnLoadMoreListener(new OnLoadMoreListener() {
										
						public void onLoadMore() {
							// Do the work to load more items at the end of list here
							new LoadMoreDataTask(self).execute();
						}
					});
					
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

	@Override
	public void doInBackgroundLoadMore(Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostExecuteLoadMore(Object value) {
		// TODO Auto-generated method stub
		
		// We need notify the adapter that the data have been changed
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();

		// Call onLoadMoreComplete when the LoadMore task, has finished
		((PullAndLoadListView) getListView()).onLoadMoreComplete();
	}

	@Override
	public void onCancelledLoadMore() {
		// TODO Auto-generated method stub
		
		// Notify the loading more operation has finished
		((PullAndLoadListView) getListView()).onLoadMoreComplete();
	}

	@Override
	public void doInBackgroundPullToRefresh(Object value) {
		// TODO Auto-generated method stub		
		AsynConnection connection = new AsynConnection(this, urlProduct, 1, Singleton.getInstance().getStringPreferences(self, "username"), Singleton.getInstance().getStringPreferences(self, "password"));
		connection.asyncConnectRequest(null, RequestType.GET);
	}

	@Override
	public void onPostExecutePullToRefresh(Object value) {
		// TODO Auto-generated method stub
		
		// We need notify the adapter that the data have been changed
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();

		// Call onLoadMoreComplete when the LoadMore task, has finished
		((PullAndLoadListView) getListView()).onRefreshComplete();
	}

	@Override
	public void onCancelledPullToRefresh() {
		// TODO Auto-generated method stub
	
		// Notify the loading more operation has finished
		((PullAndLoadListView) getListView()).onLoadMoreComplete();
	}

}
