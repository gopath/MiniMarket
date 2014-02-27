package ac.id.itb.d4.minimart.costumer.view;

import java.util.ArrayList;
import java.util.Vector;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.appmsg.AppMsg;
import com.google.zxing.client.android.CaptureActivity;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardGridView;
import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection;
import ac.id.itb.d4.minimart.costumer.connection.ConnectionInterface;
import ac.id.itb.d4.minimart.costumer.connection.Helper;
import ac.id.itb.d4.minimart.costumer.connection.AsynConnection.RequestType;
import ac.id.itb.d4.minimart.costumer.model.Goods;
import ac.id.itb.d4.minimart.costumer.model.Transaction;
import ac.id.itb.d4.minimart.costumer.parser.GoodsParser;
import ac.id.itb.d4.minimart.costumer.utils.AppConstant;
import ac.id.itb.d4.minimart.costumer.utils.Singleton;
import ac.id.itb.d4.minimart.costumer.utils.TransparentProgressDialog;
import ac.id.itb.d4.minimart.costumer.utils.sqlite.SqliteDatabaseHelper;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class GoodsActivity extends SherlockFragmentActivity implements ConnectionInterface {
	
	private GoodsActivity self = this;
	private ActionBar mActionBar;
	private ImageView tapToRefresh;
	private int index = 0;
	private SqliteDatabaseHelper sqliteHelper;
	private SharedPreferences sessions;
	private TransparentProgressDialog progressDialog;
	private String urlGoods;
	private Vector<Goods> goodsJsonData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_goods_grid_gplay);
		
		// Activate Action Bar
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setTitle("Goods");
		
		sessions = getSharedPreferences("SESSION", 0);		
		sqliteHelper = new SqliteDatabaseHelper(self);		
		
		doGoods();		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(Menu.NONE, R.id.action_new, Menu.NONE, "")
        .setIcon(R.drawable.ic_action_new)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		menu.add(Menu.NONE, R.id.action_barcode, Menu.NONE, "")
        .setIcon(R.drawable.ic_action_barcode)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		menu.add(Menu.NONE, R.id.action_cart, Menu.NONE, "")
        .setIcon(R.drawable.ic_action_cart_empty)
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
		case R.id.action_barcode:
			
			intent = new Intent(self, CaptureActivity.class);
			intent.putExtra("SCAN_MODE", "BARCODE_CODE_MODE");
			startActivityForResult(intent, 0);
			
//			intent = new Intent(self, BarcodeActivity.class);
//			startActivity(intent);
			
//			if(Singleton.getInstance().getBooleanPreferences(self, "isTransaction") == true){
//				Toast.makeText(self, "Barcode", Toast.LENGTH_SHORT).show();
//			} else {
//				AppMsg appMsg = AppMsg.makeText(self, "Please start transaction first", AppMsg.STYLE_ALERT);
//				appMsg.show();
//			}
			
			return true;			
		case R.id.action_cart:
			
			if(Singleton.getInstance().getBooleanPreferences(self, "isTransaction") == true){
				intent = new Intent(self, CartActivity.class);
				startActivity(intent);
			} else {
				AppMsg appMsg = AppMsg.makeText(self, "Please start transaction first", AppMsg.STYLE_ALERT);
				appMsg.show();
			}			
			
			return true;
		case R.id.action_new:
			if(Helper.isOnline(self)){
				if(Singleton.getInstance().getBooleanPreferences(self, "isTransaction") == true){
					
					AppMsg appMsg = AppMsg.makeText(self, "You are already start transaction", AppMsg.STYLE_ALERT);
					appMsg.show();
					
				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(self);
			   		 
		            // Setting Dialog Title
		            alertDialog.setTitle("WARNING");         
		            // Setting Dialog Message
		            alertDialog.setMessage("Are you sure want to start transaction?");
		     
		            // Setting Icon to Dialog
		            //alertDialog.setIcon(R.drawable.ic_warning);
		     
		            // Setting Positive "Yes" Button
		            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog,int which) {
		                	SharedPreferences.Editor edit = sessions.edit();
		        			edit.putBoolean("isTransaction", true);
		        			edit.commit();                    	
		        			
		        			AppMsg appMsg = AppMsg.makeText(self, "Your transaction have been recorded", AppMsg.STYLE_CONFIRM);
							appMsg.show();
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

				}			
			} else {
				AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
				appMsg.show();	
			}
									
			return true;
		default:
			return false;
		}
	}
	
	private void doGoods(){
		//urlGoods = "http://" + Singleton.getInstance().getDefaultPreferences(self, "prefAddress") + AppConstant.URL_GOODS;		
		urlGoods = AppConstant.BASE_URL + AppConstant.URL_GOODS;		
		goodsAction(urlGoods);
	}
	
	private void goodsAction(String url){
		if (Helper.isOnline(self)) {
			progressDialog = new TransparentProgressDialog(self, R.drawable.progress);
			progressDialog.show();
			
			Log.i("Login URL", url);

			AsynConnection connection = new AsynConnection(self, url, 1, "", "");						
			connection.asyncConnectRequest(null, RequestType.GET);
		} else {
			AppMsg appMsg = AppMsg.makeText(self, "No Internet Connection", AppMsg.STYLE_ALERT);
			appMsg.show();			
			
			setContentView(R.layout.no_connection);
			tapToRefresh = (ImageView) findViewById(R.id.tapToRefresh);
			tapToRefresh.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					doGoods();
				}
			});
		}
	}
		
	private void initCards() {

		Vector<Goods> goodsData = sqliteHelper.getAllGoodsData();
		
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < goodsData.size(); i++) {

        	index = i;
        	
            GplayGridCard card = new GplayGridCard(self);

            card.headerTitle = goodsData.get(i).getGoodsName();
            card.secondaryTitle = goodsData.get(i).getGoodsBarcode();
            card.resourceIdThumbnail = R.drawable.ic_launcher;            

            card.init();
            cards.add(card);
        }

        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(self, cards);

        CardGridView listView = (CardGridView)findViewById(R.id.card_grid_base);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
        
    }

	public class GplayGridCard extends Card {

		protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected int resourceIdThumbnail = -1;
        protected int count;

        protected String headerTitle;
        protected String secondaryTitle;
        
        public GplayGridCard(Context context) {
            super(context, R.layout.card_gplay_inner_content);
        }

        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {
        	CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);            

            addCardHeader(header);

            GplayGridThumb thumbnail = new GplayGridThumb(getContext());
            if (resourceIdThumbnail > -1)
                thumbnail.setDrawableResource(resourceIdThumbnail);
            else
                thumbnail.setDrawableResource(R.drawable.ic_launcher);            	
            addCardThumbnail(thumbnail);
            count = index;
            
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    //Do something
                	
                	if(Singleton.getInstance().getBooleanPreferences(self, "isTransaction") == true){
                		Vector<Goods> goodsData = sqliteHelper.getAllGoodsData();           		                    	
                		Transaction transactionBarcode = sqliteHelper.getTransactionDataByBarcode(goodsData.get(count).getGoodsBarcode());
                								
                		if(transactionBarcode.getGoodsBarcode() != null){
                			String qty = String.valueOf(Integer.parseInt(transactionBarcode.getGoodsQty())+1);
                			AppMsg appMsg = AppMsg.makeText(self, goodsData.get(count).getGoodsName() + " Qty : " + qty, AppMsg.STYLE_INFO);
                    		appMsg.show();
                    		String qtyUpdate = String.valueOf((Integer.parseInt(transactionBarcode.getGoodsQty()) + 1));
                    		transactionBarcode.setGoodsQty(qtyUpdate);
                    		sqliteHelper.updateTranscationDataQty(transactionBarcode);
                		} else {
                        	Transaction transactionBean = new Transaction();
                        	transactionBean.setTransactionId("1");
                        	transactionBean.setEmployeeId("123");
                        	transactionBean.setGoodsBarcode(goodsData.get(count).getGoodsBarcode());
                        	transactionBean.setGoodsQty("1");
                        	sqliteHelper.insertTransaction(transactionBean);
                        	
                        	AppMsg appMsg = AppMsg.makeText(self, goodsData.get(count).getGoodsName() + " has add to cart", AppMsg.STYLE_INFO);
    						appMsg.show();
                		}                		
                		 
                	}  else {
                		AppMsg appMsg = AppMsg.makeText(self, "Please click new icon to start transaction", AppMsg.STYLE_ALERT);
						appMsg.show();
                	}                	
                	
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
        	
            TextView subtitle = (TextView) view.findViewById(R.id.card_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);            
            
        }

        class GplayGridThumb extends CardThumbnail {

            public GplayGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                //viewImage.getLayoutParams().width = 196;
                //viewImage.getLayoutParams().height = 196;

            }
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
					Goods goods = new Goods();
					GoodsParser goodParser = new GoodsParser();
					goodsJsonData = goodParser.parse(value.toString());										
					
					// insert fresh data of goods if goods's data in database is empty
					if(sqliteHelper.getGoodsDataCount() < 0){
						for (int i = 0; i < goodsJsonData.size(); i++) {							
							goods.setGoodsNo(goodsJsonData.get(i).getGoodsNo());
							goods.setGoodsBarcode(goodsJsonData.get(i).getGoodsBarcode());
							goods.setGoodsName(goodsJsonData.get(i).getGoodsName());
							goods.setGoodsCategory(goodsJsonData.get(i).getGoodsCategory());
							sqliteHelper.insertGoods(goods);
						}
					} else {
						// already good's data in database, then delete it first, after that insert new good's data
						Vector<Goods> v = sqliteHelper.getAllGoodsData();
						for (int i = 0; i < v.size(); i++) {
							sqliteHelper.deleteGoodsData(v.get(i).getGoodsNo());
						}
						
						for (int i = 0; i < goodsJsonData.size(); i++) {							
							goods.setGoodsNo(goodsJsonData.get(i).getGoodsNo());
							goods.setGoodsBarcode(goodsJsonData.get(i).getGoodsBarcode());
							goods.setGoodsName(goodsJsonData.get(i).getGoodsName());
							goods.setGoodsCategory(goodsJsonData.get(i).getGoodsCategory());
							sqliteHelper.insertGoods(goods);
						}
					}										
					
					initCards();
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
		
		AppMsg appMsg = AppMsg.makeText(self, "Error Loading Goods's Data", AppMsg.STYLE_CONFIRM);
		appMsg.show();			
		
		initCards();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		
		String strFormat, strResult;
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				strFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
				strResult = intent.getStringExtra("SCAN_RESULT");
				Toast.makeText(this, strFormat + " : " + strResult, Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				strResult = "Scan dibatalkan";
				Toast.makeText(this, strResult, Toast.LENGTH_SHORT).show();
				
				intent = new Intent(self, GoodsActivity.class);
				startActivity(intent);
				self.finish();				
			}
		}
	}
}
