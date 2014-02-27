package ac.id.itb.d4.minimart.inventory.adapter;

import java.util.Vector;
 
import ac.id.itb.d4.minimart.inventory.R;
import ac.id.itb.d4.minimart.inventory.model.Account;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class AccountListAdapter extends BaseAdapter {
 
	private Activity activity;
    private Vector<Account> data;
    private static LayoutInflater inflater = null;
    
    public AccountListAdapter(Activity a, Vector<Account> d) {
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
            vi = inflater.inflate(R.layout.list_row_report_in, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        Account account = new Account();
        account = data.get(position);
        
        // Setting all values in listview
        title.setText(account.getFullName());
        artist.setText(account.getVcard());
        duration.setText(account.getCreatedAt());
        return vi;
    }
}