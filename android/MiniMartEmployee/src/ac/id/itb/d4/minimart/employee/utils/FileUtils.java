package ac.id.itb.d4.minimart.employee.utils;

import java.io.File;

import android.os.Environment;

public class FileUtils {

	public boolean fileExist(String fileName){
		File file = new File(Environment.getExternalStorageDirectory().getPath() + "/GaneshaStore/" + fileName);

		if(file.exists()) return true;
		else return false;
	}
	
	public void deleteFile(String path){
		File file = new File(path);
		if(file.exists())file.delete();
	}
	
}
