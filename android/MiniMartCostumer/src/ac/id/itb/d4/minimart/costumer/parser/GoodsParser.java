package ac.id.itb.d4.minimart.costumer.parser;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.id.itb.d4.minimart.costumer.model.Goods;
import android.util.Log;

public class GoodsParser {

	public Vector<Goods> parse(String data) {
		Vector<Goods> v = new Vector<Goods>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("goods");
            for (int i = 0; i < jsonArray.length(); i++) {
                v.addElement(parseData(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return v;
    }

    private Goods parseData(JSONObject object) {
    	Goods bean = new Goods();
        try {
        	
        	bean.setGoodsNo(object.getInt("no"));
        	bean.setGoodsBarcode(object.getString("code"));
        	bean.setGoodsName(object.getString("name"));
        	bean.setGoodsCategory(object.getString("category"));
        	
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("Goods Parser", bean.toString());
        return bean;
    }
	
}
