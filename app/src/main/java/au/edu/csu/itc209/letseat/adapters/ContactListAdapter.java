package au.edu.csu.itc209.letseat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

import au.edu.csu.itc209.letseat.R;


public class ContactListAdapter extends BaseAdapter {
    private JSONArray contacts;
    private Context ctx;

    public ContactListAdapter(Context ctx, JSONArray contacts) {
        this.ctx = ctx;
        this.contacts = contacts;
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return (JSONObject) this.contacts.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        View vi = view;

//        if(vi==null) {
//            LayoutInflater layout;
//
//            layout = LayoutInflater.from(ctx);
//            vi = layout.inflate(R.layout.list_row_contacts, null);
//        }
//
//        TextView tvId = (TextView) vi.findViewById(R.id.tvId);
//        TextView tvEmail = (TextView) vi.findViewById(R.id.tvEmail);
//        TextView tvName = (TextView) vi.findViewById(R.id.tvName);
//        try {
//            JSONObject obj = getItem(i);
//            if(obj!=null) {
//                if (tvId != null) {
//                    tvId.setText(obj.getString("id"));
//                }
//
//                if (tvEmail != null) {
//                    tvEmail.setText(obj.getString("email"));
//                }
//
//                if (tvName != null) {
//                    tvName.setText(obj.getString("email"));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return vi;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
