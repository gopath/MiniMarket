package ac.id.itb.d4.minimart.costumer.utils.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class SqliteCopyDatabase extends SQLiteOpenHelper {
	
	private Context ctx;    
	private static final int DATABASE_VERSION = 1;
    private static String DB_FILEPATH = "minimart.sqlite";
    private static String DB_SDCARD = Environment.getExternalStorageDirectory() + "/minimart/databases/";
    
	public SqliteCopyDatabase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public SqliteCopyDatabase(Context context){
		super(context, DB_FILEPATH, null, DATABASE_VERSION);
		ctx = context;
		createDataBase();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void createDataBase() {
    	final boolean dbExist = checkDataBase();
    	if (dbExist) {
    		//do nothing - database already exist
    		Log.i("SQLITE DB", "DB Exist");    		
    	} else {
    		//By calling this method and empty database will be created into the default system path
    		//of your application so we are gonna be able to overwrite that database with our database.
    		//By calling this method and empty database will be created into the default system path 
    		//of your application so we are gonna be able to overwrite that database with our database. 
    		this.getReadableDatabase();
    		try {
    			copyDataBase();
 		   	} catch (IOException e) {
 		   		Log.i("SQLITE DB", "Error : " + e.getMessage());		  
 		   	}
    	}
    }
        
    public boolean checkDataBase() {
    	File dbFile = new File(DB_SDCARD + DB_FILEPATH);
    	return dbFile.exists();
    }    
    
    private void copyDataBase() throws IOException {
			//Open your local db as the input stream	    	
			InputStream myInput = ctx.getAssets().open(DB_FILEPATH);
			
			// Path to the just created empty db
	    	String outFileName = DB_SDCARD + DB_FILEPATH;
	    	
	    	// create folder
	    	int indexBackSlash = 0;
	    	File fFolder = new File(outFileName);
	    	String folder1, folder2;
	    	if(!fFolder.exists()){
	    		indexBackSlash = outFileName.lastIndexOf("/");		    	
	    		folder1 = outFileName.substring(0, indexBackSlash);
	    		indexBackSlash = folder1.lastIndexOf("/");
	    		folder2 = folder1.substring(0, indexBackSlash);
	    		File tempFolder1 = new File(folder1);
	    		File tempFolder2 = new File(folder2);
	    		if(!tempFolder2.exists()){
	    			tempFolder2.mkdir();
	    			if(!tempFolder1.exists()){
	    				tempFolder1.mkdir();
	    			} else {
	    				Log.i("SQLITE DB", folder1 + " exist!");		    			
	    			}
	    		} else {
	    			Log.i("SQLITE DB", folder2 + " exist!");	    			
	    		}
	    	}
	    	
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(outFileName);

	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer)) > 0) {
	    		myOutput.write(buffer, 0, length);
	    	}

	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	    	
	    	Log.i("SQLITE DB", "Database copied");	    	
    }
    
    public void openDataBase(SQLiteDatabase myDataBase) {
    	//Open the database
    	String myPath = DB_SDCARD + DB_FILEPATH;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
