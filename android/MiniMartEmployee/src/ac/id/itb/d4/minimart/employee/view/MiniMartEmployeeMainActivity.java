package ac.id.itb.d4.minimart.employee.view;

import ac.id.itb.d4.minimart.employee.R;
import ac.id.itb.d4.minimart.employee.R.layout;
import ac.id.itb.d4.minimart.employee.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MiniMartEmployeeMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mini_mart_employee_main, menu);
		return true;
	}

}
