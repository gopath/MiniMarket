package ac.id.itb.d4.minimart.purchasing.adapter;

import java.util.Vector;
 
import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.model.GoodReceive;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class GoodReceiveListAdapter extends BaseAdapter {
 
	private Activity activity;
    private Vector<GoodReceive> data;
    private static LayoutInflater inflater = null;
    
    public GoodReceiveListAdapter(Activity a, Vector<GoodReceive> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_product, null);

        TextView productName = (TextView)vi.findViewById(R.id.productName);
        TextView dtlDescription = (TextView)vi.findViewById(R.id.productDtlDescription);
        TextView qty = (TextView)vi.findViewById(R.id.productDefaultQuantity);
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        GoodReceive product = new GoodReceive();
        product = data.get(position);
        
        // Setting all values in listview
        productName.setText(product.getProductName());
        dtlDescription.setText(product.getDetailsDescription());
        qty.setText("Qty : " + String.valueOf(product.getDefaultQty()));
        return vi;
    }
}