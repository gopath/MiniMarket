package ac.id.itb.d4.minimart.costumer.utils.sqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.os.Environment;

public class FileUtils {
    /**
     * Creates the specified <code>toFile</code> as a byte for byte copy of the
     * <code>fromFile</code>. If <code>toFile</code> already exists, then it
     * will be replaced with a copy of <code>fromFile</code>. The name and path
     * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
     * <br/>
     * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
     * this function.</i>
     * 
     * @param fromFile
     *            - FileInputStream for the file to copy from.
     * @param toFile
     *            - FileInputStream for the file to copy to.
     */
    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
        	fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();            
            try {
				fromChannel.transferTo(0, fromChannel.size(), toChannel);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
                if (toChannel != null) {
                    try {
						toChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }
    }
    
    public static void close(InputStream stream) {
        if(stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    public static void close(OutputStream stream) {
        if(stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void copyDatabase(File fromFile, File toFile){
    	FileInputStream fis;
    	OutputStream output;
		try {
			fis = new FileInputStream(fromFile);

			// Open the empty db as the output stream
			output = new FileOutputStream(toFile);

	        // Transfer bytes from the inputfile to the outputfile
	        byte[] buffer = new byte[1024];
	        int length;
	        try {
				while ((length = fis.read(buffer))>0){
				    output.write(buffer, 0, length);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error IOException : " + e.getMessage());
			}
	        

	        // Close the streams
	        try {
				output.flush();
				output.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error close IOException : " + e.getMessage());
			}
	      
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error FileNotFoundException : " + e1.getMessage());
		}
    }
    
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