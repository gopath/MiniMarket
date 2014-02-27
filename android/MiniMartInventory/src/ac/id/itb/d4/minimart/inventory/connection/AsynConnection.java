package ac.id.itb.d4.minimart.inventory.connection;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsynConnection extends AsyncHttpResponseHandler {
	
	private ConnectionInterface callback;
	private String urlToConnect;
	private int type;
	private String authUsername;
	private String authPassword;
	private final int timeout = 60000;
	
	private HashMap<String, String> hParams;
	private RequestParams rParams;
	
	public enum RequestType{
		GET, POST
	};
	
	public AsynConnection(ConnectionInterface connectionInterface, String url, int type,
			String username, String password) {
		// TODO Auto-generated constructor stub
		this.callback = connectionInterface;
		this.urlToConnect = url;
		this.type = type;
		this.authUsername = username;
		this.authPassword = password;
	}
	
	public void asyncConnectRequest(HashMap<String, String> params, RequestType request){
		this.hParams = params;
		this.rParams = new RequestParams(hParams);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.setBasicAuth(authUsername, authPassword);
		client.setTimeout(timeout);
		if(request == RequestType.GET){
			client.get(urlToConnect, this);
		} else if(request == RequestType.POST){
			client.post(urlToConnect, rParams, this);
		}
	}
		
	@Override
	public void onSuccess(int statusCode, String content) {
		// TODO Auto-generated method stub
		super.onSuccess(statusCode, content);				
		callback.callBackOnSuccess(content, statusCode, type);
	}
	
	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		// TODO Auto-generated method stub
		super.onFailure(statusCode, error, content);
		callback.callBackOnFailed(content, statusCode, type);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
	}
	
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
	
	}
		
}
