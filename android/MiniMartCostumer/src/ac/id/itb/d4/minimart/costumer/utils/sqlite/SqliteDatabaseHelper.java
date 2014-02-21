package ac.id.itb.d4.minimart.costumer.utils.sqlite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import ac.id.itb.d4.minimart.costumer.model.Goods;
import ac.id.itb.d4.minimart.costumer.model.Transaction;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {
	 
    public SqliteDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub		
	}

	// All Static variables
    // Database Version
    private Context ctx;
    private static final int DATABASE_VERSION = 1;
    private static String DB_FILEPATH = "minimart.sqlite";
    private static String DB_SDCARD = Environment.getExternalStorageDirectory() + "/minimart/databases/";

    // Database Name
    private static final String DATABASE_NAME = "minimart";
 
    // Contacts table name
    private String TABLE_GOODS = "minimart_goods";
    private String TABLE_TRANSACTION = "minimart_transaction";
    
    // Contacts Table Columns names
    private String GOODS_NO = "goods_no";
    private String GOODS_BARCODE = "goods_barcode";
    private String GOODS_NAME = "goods_name";
    private String GOODS_CATEGORY = "goods_category";    
    private String TRANSACTION_ID = "transaction_id";
    private String EMPLOYEE_ID = "employee_id";
    private String GOODS_QTY = "goods_qty";
        
    public SqliteDatabaseHelper(Context context) {
    	super(context, DB_SDCARD + DB_FILEPATH, null, DATABASE_VERSION);
        ctx = context;        
    }    
     
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public boolean exportDatabase() {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        //close();
        File newDb = new File(DB_SDCARD);
        File oldDb = new File(DB_FILEPATH);
		System.out.println("Size db : " + oldDb.getTotalSpace() + " - " + oldDb.getUsableSpace());	            
        if (newDb.exists()) {
            FileUtils.copyDatabase(oldDb, newDb);
			System.out.println("[Exist] Copy from " + oldDb.getAbsolutePath() + " to " + newDb.getAbsolutePath());
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            //getWritableDatabase().close();
            return true;
        } else {
        	try {
        		String dbDir = DB_SDCARD.substring(0, DB_SDCARD.lastIndexOf("/")+1);
        		File fileFBDir = new File(dbDir);
        		fileFBDir.mkdir();
        		newDb.createNewFile();
        		System.out.println("Creat DB Folder : " + dbDir + " - creating database " + newDb.getAbsolutePath());        		        		
        		
        		// copy database
        		FileUtils.copyDatabase(oldDb, newDb);
    			System.out.println("[Not Exist] Copy from " + oldDb.getAbsolutePath() + " to " + newDb.getAbsolutePath());
                
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        return false;
    }        
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
 
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        
        // Create tables again
        onCreate(db);
    }                    
    
    public long insertTransaction(Transaction bean){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_ID, Integer.parseInt(bean.getTransactionId()));
		values.put(EMPLOYEE_ID, bean.getEmployeeId());
		values.put(GOODS_BARCODE, bean.getGoodsBarcode());
		values.put(GOODS_QTY, bean.getGoodsQty());
		
		long transactionId = db.insert(TABLE_TRANSACTION, null, values);
		
		return transactionId;
	}
    
    public Transaction getTransactionDataById(int id){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String selectQuery = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE "
    			+ TRANSACTION_ID +  " = " + id;
    	Log.e("LOG", selectQuery);
    	Cursor c = db.rawQuery(selectQuery, null);
    	
    	Transaction bean = new Transaction();
    	if(c != null && c.getCount() > 0){
    		c.moveToFirst();
        	
        	bean.setTransactionId(Integer.toString(c.getInt(c.getColumnIndex(TRANSACTION_ID))));
        	bean.setEmployeeId(c.getString(c.getColumnIndex(EMPLOYEE_ID)));
        	bean.setGoodsBarcode(c.getString(c.getColumnIndex(GOODS_BARCODE)));
        	bean.setGoodsQty(Integer.toString(c.getInt(c.getColumnIndex(GOODS_QTY))));
    	}    	
    	
    	return bean;
    }
    
    public Transaction getTransactionDataByBarcode(String barcode){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String selectQuery = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE "
    			+ GOODS_BARCODE +  " = \"" + barcode + "\"";
    	Log.e("LOG", selectQuery);
    	Cursor c = db.rawQuery(selectQuery, null);
    	
    	Transaction bean = new Transaction();    	
    	if(c != null && c.getCount() > 0){
    		c.moveToFirst();
        	
        	bean.setTransactionId(Integer.toString(c.getInt(c.getColumnIndex(TRANSACTION_ID))));
        	bean.setEmployeeId(c.getString(c.getColumnIndex(EMPLOYEE_ID)));
        	bean.setGoodsBarcode(c.getString(c.getColumnIndex(GOODS_BARCODE)));
        	bean.setGoodsQty(Integer.toString(c.getInt(c.getColumnIndex(GOODS_QTY))));
    	}
    	
    	
    	return bean;
    }
    
    public Vector<Transaction> getAllTransactionData() {
        Vector<Transaction> transData = new Vector<Transaction>();
        String selectQuery = "select minimart_transaction.transaction_id,  minimart_transaction.employee_id, minimart_goods.goods_barcode, minimart_goods.goods_name,  minimart_transaction.goods_qty" 
        + " from minimart_transaction, minimart_goods"
        + " where minimart_transaction.goods_barcode = minimart_goods.goods_barcode";
 
        Log.e("LOG", selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Transaction bean = new Transaction();
            	bean.setTransactionId(Integer.toString(c.getInt(c.getColumnIndex(TRANSACTION_ID))));
            	bean.setEmployeeId(c.getString(c.getColumnIndex(EMPLOYEE_ID)));
            	bean.setGoodsName(c.getString(c.getColumnIndex(GOODS_NAME)));
            	bean.setGoodsBarcode(c.getString(c.getColumnIndex(GOODS_BARCODE)));
            	bean.setGoodsQty(Integer.toString(c.getInt(c.getColumnIndex(GOODS_QTY))));
                
                // adding to list
            	transData.add(bean);
            } while (c.moveToNext());
        }
 
        return transData;
    }
        
    public int getTransactionDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRANSACTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        int count = cursor.getCount();
        cursor.close();
 
        // return count
        return count;
    }
    
    public int updateTranscationDataQty(Transaction bean) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
		values.put(GOODS_QTY, bean.getGoodsQty());
		
        // updating row
        return db.update(TABLE_TRANSACTION, values, GOODS_BARCODE + " = " + bean.getGoodsBarcode(), null);
    }
 
    public void deleteTransactionData(long trans_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTION, TRANSACTION_ID + " = ?",
                new String[] { String.valueOf(trans_id) });
    }
    
    public long insertGoods(Goods bean){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(GOODS_NO, bean.getGoodsNo());
		values.put(GOODS_BARCODE, bean.getGoodsBarcode());
		values.put(GOODS_NAME, bean.getGoodsName());
		values.put(GOODS_CATEGORY, bean.getGoodsCategory());
		
		long goodsId = db.insert(TABLE_GOODS, null, values);
		
		return goodsId;
	}
    
    public Goods getGoodsDataByNo(String id){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String selectQuery = "SELECT * FROM " + TABLE_GOODS + " WHERE "
    			+ GOODS_NO +  " = " + id;
    	Log.e("LOG", selectQuery);
    	Cursor c = db.rawQuery(selectQuery, null);
    	
    	if(c != null)
    		c.moveToFirst();
    	
    	Goods bean = new Goods();
    	bean.setGoodsNo(Integer.parseInt(c.getString(c.getColumnIndex(GOODS_NO))));
    	bean.setGoodsBarcode(c.getString(c.getColumnIndex(GOODS_BARCODE)));
    	bean.setGoodsName(c.getString(c.getColumnIndex(GOODS_NAME)));
    	bean.setGoodsCategory(c.getString(c.getColumnIndex(GOODS_CATEGORY)));
    	
    	return bean;
    }
    
    public Vector<Goods> getAllGoodsData() {
        Vector<Goods> goodsData = new Vector<Goods>();
        String selectQuery = "SELECT  * FROM " + TABLE_GOODS;
 
        Log.e("LOG", selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Goods bean = new Goods();
            	bean.setGoodsNo(Integer.parseInt(c.getString(c.getColumnIndex(GOODS_NO))));
            	bean.setGoodsBarcode(c.getString(c.getColumnIndex(GOODS_BARCODE)));
            	bean.setGoodsName(c.getString(c.getColumnIndex(GOODS_NAME)));
            	bean.setGoodsCategory(c.getString(c.getColumnIndex(GOODS_CATEGORY)));
                
                // adding to list
            	goodsData.add(bean);
            } while (c.moveToNext());
        }
 
        return goodsData;
    }
        
    public int getGoodsDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GOODS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        int count = cursor.getCount();
        cursor.close();
 
        // return count
        return count;
    }
    
    public int updateGoodsData(Goods bean) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
		values.put(GOODS_NO, bean.getGoodsNo());
		values.put(GOODS_BARCODE, bean.getGoodsBarcode());
		values.put(GOODS_NAME, bean.getGoodsName());
		values.put(GOODS_CATEGORY, bean.getGoodsCategory());
		
        // updating row
        return db.update(TABLE_GOODS, values, GOODS_NO + " = ?",
                new String[] { String.valueOf(bean.getGoodsNo()) });
    }
 
    public void deleteGoodsData(long goods_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOODS, GOODS_NO + " = ?",
                new String[] { String.valueOf(goods_no) });
    }
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
 
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    
}