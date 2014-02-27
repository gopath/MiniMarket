package ac.id.itb.d4.minimart.costumer.view;

import java.util.HashMap;
import java.util.Vector;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.appmsg.AppMsg;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection;
import ac.id.itb.d4.minimart.costumer.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.costumer.connection.Helper;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.costumer.model.Transaction;
import ac.id.itb.d4.minimart.costumer.parser.TransactionParser;
import ac.id.itb.d4.minimart.costumer.utils.AppConstant;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import ac.id.itb.d4.minimart.costumer.utils.TransparentProgressDialog;
import ac.id.itb.d4.minimart.costumer.utils.sqlite.SqliteDatabaseHelper;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CartActivity extends SherlockFragmentActivity implements ConnectionInterface {
	
	private CartActivity self = this;
	private ActionBar mActionBar;
	private SqliteDatabaseHelper sqliteHelper;
	private TableLayout table;
	private TextView tvPrice;
	private TransparentProgressDialog progressDialog;
	private String employeeId = "49012077";
	private String urlCartPayment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
	
		sqliteHelper = new SqliteDatabaseHelper(self);
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Cart");						
		
		tvPrice = (TextView) findViewById(R.id.tvPrice);
		tvPrice.setVisibility(View.INVISIBLE);
		table = (TableLayout) findViewById(R.id.main_table);		
		initTable();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(Menu.NONE, R.id.action_payment, Menu.NONE, "")
        .setIcon(R.drawable.ic_action_payment)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(self, HomeTabActivity.class);
			startActivity(intent);
			self.finish();
			return true;
		case R.id.action_payment:
		
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(self);
	   		 
            // Setting Dialog Title
            alertDialog.setTitle("WARNING");         
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure want to finish transaction and pay the goods?");
     
            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.ic_warning);
     
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                	doCartPayment();
                }
            });
     
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	dialog.cancel();
                }
            });
     
            // Showing Alert Message
            alertDialog.show();
			
			return true;
		default:
			return false;
		}
	}		
	
	@SuppressLint("ResourceAsColor")
	private void initTable(){
		
		TableRow rowHead = new TableRow(this);
		rowHead.setId(10);
		rowHead.setBackgroundColor(getResources().getColor(R.color.main_background_end));
		rowHead.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));		
		
		TextView lblBarcode = new TextView(this);
		lblBarcode.setId(1);
		lblBarcode.setText("BARCODE");
		lblBarcode.setTextColor(Color.WHITE);
		lblBarcode.setPadding(10, 10, 10, 10);
        rowHead.addView(lblBarcode);

        TextView lblName = new TextView(this);
        lblName.setId(2);
        lblName.setText("GOODS NAME"); 
        lblName.setTextColor(Color.WHITE); 
        lblName.setPadding(10, 10, 10, 10); 
        rowHead.addView(lblName);
        
        TextView lblQty = new TextView(this);
        lblQty.setId(3);
        lblQty.setText("GOODS QTY"); 
        lblQty.setTextColor(Color.WHITE); 
        lblQty.setPadding(10, 10, 10, 10); 
        rowHead.addView(lblQty);
        
        table.addView(rowHead);
		
		Vector<Transaction> transactionData = sqliteHelper.getAllTransactionData();
		for (int i = 0; i < transactionData.size(); i++) {
			//Toast.makeText(self, transactionData.get(i).getGoodsName() + " - " + transactionData.get(i).getGoodsQty() , Toast.LENGTH_SHORT).show();					
	        
	        // Create the table row with value
	        TableRow rowValue = new TableRow(self);
	        if(i%2 != 0) rowValue.setBackgroundColor(getResources().getColor(R.color.gray));
	        rowValue.setId(100 + i);
	        rowValue.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.WRAP_CONTENT));

	        // Create a TextView
	        TextView valueBarcode = new TextView(self);
	        valueBarcode.setId(200 + i); 
	        valueBarcode.setText(transactionData.get(i).getGoodsBarcode());
	        valueBarcode.setPadding(5, 0, 5, 0);
	        valueBarcode.setTextColor(Color.BLACK);
	        rowValue.addView(valueBarcode);
	        
	        TextView valueName = new TextView(self);
	        valueName.setId(300 + i);
	        valueName.setText(transactionData.get(i).getGoodsName());
	        valueName.setPadding(5, 0, 5, 0);
	        valueName.setTextColor(Color.BLACK);
	        rowValue.addView(valueName);
	        
	        TextView valueQty = new TextView(this);
	        valueQty.setId(400 + i);
	        valueQty.setText(transactionData.get(i).getGoodsQty());
	        valueQty.setPadding(5, 0, 5, 0);
	        valueQty.setTextColor(Color.BLACK);
	        rowValue.addView(valueQty);
	        
	        table.addView(rowValue);
	        
		}
				
	}

	private void doCartPayment(){
		//urlCartPayment = "http://" + Singleton.getInstance().getDefaultPreferences(self, "prefAddress") + AppConstant.URL_TRANSACTION;		
		urlCartPayment = AppConstant.BASE_URL + AppConstant.URL_TRANSACTION;		
		cartPaymentAction(urlCartPayment);
	}
	
	private void cartPaymentAction(String url){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.progress);
			progressDialog.show();
			
			Log.i("Paymeny URL", url);

			Vector<Transaction> transactionData = sqliteHelper.getAllTransactionData();
			
			AsynConnection connection = new AsynConnection(self, url, 1, "", "");						
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("key", "opencrx12345");
			params.put("txtSearch", transactionData.get(0).getGoodsBarcode());
			params.put("emp_id", employeeId);			
			
			Log.i("Param", params.toString());
			
			connection.asyncConnectRequest(params, RequestType.POST);
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();			
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
					Log.i("Payment Result", value.toString());
					
					TransactionParser transParser = new TransactionParser();
					Vector<Transaction> vTrans = transParser.parse(value.toString());
					String result = vTrans.get(0).getStatus();
					if(result.matches("5")){
						AppMsg appMsg = AppMsg.makeText(self, "Payment Done", AppMsg.STYLE_INFO);
						appMsg.show();
						
						sqliteHelper.deleteTransactionData(1);
						Intent intent = new Intent(self, HomeTabActivity.class);
						startActivity(intent);
						
					} else {
						AppMsg appMsg = AppMsg.makeText(self, "Something wrong with payment", AppMsg.STYLE_ALERT);
						appMsg.show();
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

}
