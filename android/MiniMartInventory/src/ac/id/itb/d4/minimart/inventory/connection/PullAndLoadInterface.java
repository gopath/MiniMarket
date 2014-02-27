package ac.id.itb.d4.minimart.inventory.connection;

public interface PullAndLoadInterface {
	public void doInBackgroundLoadMore(Object value);
	public void onPostExecuteLoadMore(Object value);
	public void onCancelledLoadMore();
	public void doInBackgroundPullToRefresh(Object value);
	public void onPostExecutePullToRefresh(Object value);
	public void onCancelledPullToRefresh();
}
