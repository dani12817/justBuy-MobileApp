package danielleal.justbuy.classes.listViewItems.adapterList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import danielleal.justbuy.classes.listViewItems.searchShopItemList;

import danielleal.justbuy.R;

/**
 * Created by Dani on 16/02/2018.
 */

public class adapterSearchShop extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<searchShopItemList> items;

    public adapterSearchShop (Activity activity, ArrayList<searchShopItemList> items) {
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

    public void addAll(ArrayList<searchShopItemList> shopsSearched) {
        for (int i = 0; i < shopsSearched.size(); i++) {
            items.add(shopsSearched.get(i));
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
            v = inf.inflate(R.layout.list_item_search_shop, null);
        }

        searchShopItemList shop = items.get(position);

        TextView title = v.findViewById(R.id.labelSearchShopName);
        title.setText(shop.getName());

        if(shop.getLogo() != null) {
            ImageView imagelogo = v.findViewById(R.id.imageSearchShopLogo);
            imagelogo.setImageBitmap(
                    Bitmap.createScaledBitmap(
                            BitmapFactory.decodeByteArray(shop.getLogo(), 0, shop.getLogo().length),
                            221,
                            221,
                            false
                    )
            );
        }

        return v;
    }
}

