package ac.id.itb.d4.minimart.employee.connection;

public interface ConnectionInterface {
	public void callBackOnSuccess(Object value, int responseCode, int type);
	public void callBackOnFailed(Object value, int responseCode, int type);
	public void callBackOnStart(Object value, int responseCode, int type);
	public void callBackOnFinish(Object value, int responseCode, int type);
	public void callBackOnConnect(Object value, int responseCode, int type);		
}
