package danielleal.justbuy.classes.listViewItems.adapterList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import danielleal.justbuy.R;
import danielleal.justbuy.classes.listViewItems.addressItemList;

/**
 * Created by Dani on 29/01/2018.
 */

public class adapterAddress extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<addressItemList> items;

    public adapterAddress (Activity activity, ArrayList<addressItemList> items) {
        this.activity = activity;
        this.items = items;
        //addAll(items);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<addressItemList> addresses) {
        for (int i = 0; i < addresses.size(); i++) {
            items.add(addresses.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item_address, null);
        }

        addressItemList dir = items.get(position);

        TextView title = v.findViewById(R.id.addressName);
        title.setText(dir.getAddressName());

        TextView description = v.findViewById(R.id.fullAddress);
        description.setText(dir.getFullAddress());

        return v;
    }
}
