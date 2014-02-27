package ac.id.itb.d4.minimart.manager.adapter;

import java.util.Vector;
 
import ac.id.itb.d4.minimart.manager.R;
import ac.id.itb.d4.minimart.manager.model.Documents;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class DocumentsListAdapter extends BaseAdapter {
 
	private Activity activity;
    private Vector<Documents> data;  //Sesuai nama file di model
    private static LayoutInflater inflater = null;
    
    public DocumentsListAdapter(Activity a, Vector<Documents> d) {
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
            vi = inflater.inflate(R.layout.list_row_document, null); //layout/list_row_product

        TextView author = (TextView)vi.findViewById(R.id.textViewAuthor);
        TextView location = (TextView)vi.findViewById(R.id.textViewlocation);
        TextView name = (TextView)vi.findViewById(R.id.textViewname);
        TextView title = (TextView)vi.findViewById(R.id.textViewtitle);
        TextView description = (TextView)vi.findViewById(R.id.textViewdescription);
                
        Documents document = new Documents();
        document = data.get(position);
        
        // Setting all values in listview
        author.setText(document.getAuthor());
        location.setText(String.valueOf(document.getLocation()));
        name.setText(String.valueOf(document.getName()));
        title.setText(document.getTitle());
        description.setText(String.valueOf(document.getDescription()));
       
        
        return vi;
    }
}