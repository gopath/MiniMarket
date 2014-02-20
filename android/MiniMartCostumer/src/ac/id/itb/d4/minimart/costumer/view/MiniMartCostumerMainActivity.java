package ac.id.itb.d4.minimart.costumer.view;

import ac.id.itb.d4.minimart.costumer.R;
import ac.id.itb.d4.minimart.costumer.R.layout;
import ac.id.itb.d4.minimart.costumer.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MiniMartCostumerMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mini_mart_costumer_main, menu);
		return true;
	}

}
