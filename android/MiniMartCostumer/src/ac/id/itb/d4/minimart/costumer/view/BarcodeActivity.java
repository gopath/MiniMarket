package ac.id.itb.d4.minimart.costumer.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.zxing.client.android.CaptureActivity;

public class BarcodeActivity extends SherlockActivity {

	private BarcodeActivity self = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(self, CaptureActivity.class);
		//Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "BARCODE_CODE_MODE");
		startActivityForResult(intent, 0);
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
