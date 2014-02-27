package ac.id.itb.d4.minimart.manager.connection;

import android.os.AsyncTask;

public class LoadMoreDataTask extends AsyncTask<Object, Object, Object> {

	private PullAndLoadInterface pullandload;
	
	public LoadMoreDataTask(PullAndLoadInterface pullLoad) {
		// TODO Auto-generated constructor stub
		this.pullandload = pullLoad;
	}
	
	@Override
	protected Object doInBackground(Object... value) {
		// TODO Auto-generated method stub
		
		if (isCancelled()) {
			return null;
		}

		// Simulates a background task
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		pullandload.doInBackgroundPullToRefresh(value);
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		
		pullandload.onPostExecuteLoadMore(result);
		
		super.onPostExecute(result);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		
		pullandload.onCancelledLoadMore();
		
		super.onCancelled();
	}
}
