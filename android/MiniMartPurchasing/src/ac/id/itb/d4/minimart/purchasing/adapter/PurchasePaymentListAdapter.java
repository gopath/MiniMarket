package ac.id.itb.d4.minimart.purchasing.adapter;

import java.util.Vector;
 
import ac.id.itb.d4.minimart.purchasing.R;
import ac.id.itb.d4.minimart.purchasing.model.PurchasePayment;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class PurchasePaymentListAdapter extends BaseAdapter {
 
	private Activity activity;
    private Vector<PurchasePayment> data;
    private static LayoutInflater inflater = null;
    
    public PurchasePaymentListAdapter(Activity a, Vector<PurchasePayment> d) {
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
            vi = inflater.inflate(R.layout.list_row_account, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        PurchasePayment account = new PurchasePayment();
        account = data.get(position);
        
        // Setting all values in listview
        title.setText(account.getFullName());
        artist.setText(account.getVcard());
        duration.setText(account.getCreatedAt());
        return vi;
    }
}