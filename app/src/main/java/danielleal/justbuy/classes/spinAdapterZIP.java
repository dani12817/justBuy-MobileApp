package danielleal.justbuy.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dani on 14/02/2018.
 */

public class spinAdapterZIP extends ArrayAdapter<ZIPItem> {
    private Context context;
    private ArrayList<ZIPItem> values;

    public spinAdapterZIP(Context context, int textViewResourceId, ArrayList<ZIPItem> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public ZIPItem getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getZIPNumber());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getZIPNumber());

        return label;
    }
}
