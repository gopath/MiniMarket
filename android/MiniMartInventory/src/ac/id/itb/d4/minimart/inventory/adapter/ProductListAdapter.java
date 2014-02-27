package ac.id.itb.d4.minimart.inventory.adapter;

import java.util.Vector;
 
import ac.id.itb.d4.minimart.inventory.R;
import ac.id.itb.d4.minimart.inventory.model.StockIn;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class ProductListAdapter extends BaseAdapter {
 
	private Activity activity;
    private Vector<StockIn> data;
    private static LayoutInflater inflater = null;
    
    public ProductListAdapter(Activity a, Vector<StockIn> d) {
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
            vi = inflater.inflate(R.layout.list_row_stock_in, null);

        TextView productName = (TextView)vi.findViewById(R.id.productName);
        TextView dtlDescription = (TextView)vi.findViewById(R.id.productDtlDescription);
        TextView qty = (TextView)vi.findViewById(R.id.productDefaultQuantity);
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        StockIn product = new StockIn();
        product = data.get(position);
        
        // Setting all values in listview
        productName.setText(product.getProductName());
        dtlDescription.setText(product.getDetailsDescription());
        qty.setText("Qty : " + String.valueOf(product.getDefaultQty()));
        return vi;
    }
}