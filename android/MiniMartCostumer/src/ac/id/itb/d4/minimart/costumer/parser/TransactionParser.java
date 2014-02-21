package ac.id.itb.d4.minimart.costumer.parser;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.id.itb.d4.minimart.costumer.model.Transaction;
import android.util.Log;

public class TransactionParser {

	public Vector<Transaction> parse(String data) {
		Vector<Transaction> v = new Vector<Transaction>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("transaction");
            for (int i = 0; i < jsonArray.length(); i++) {
                v.addElement(parseData(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return v;
    }

    private Transaction parseData(JSONObject object) {
    	Transaction bean = new Transaction();
        try {
        	
        	bean.setStatus(object.getString("status"));
        	
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("Transaction Parser", bean.toString());
        return bean;
    }
	
}
