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
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import danielleal.justbuy.R;
import danielleal.justbuy.classes.listViewItems.ratingShopItemList;

/**
 * Created by Dani on 21/02/2018.
 */

public class adapterRatingShop extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<ratingShopItemList> items;

    public adapterRatingShop (Activity activity, ArrayList<ratingShopItemList> items) {
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

    public void addAll(ArrayList<ratingShopItemList> shopsSearched) {
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
            v = inf.inflate(R.layout.list_item_rating_shop, null);
        }

        ratingShopItemList rating = items.get(position);

        TextView user = v.findViewById(R.id.labelRatingUserShop);
        user.setText(rating.getUser());

        TextView text = v.findViewById(R.id.labelRatingTextShop);
        text.setText(rating.getText());

        TextView price = v.findViewById(R.id.labelRatingDateShop);
        price.setText(rating.getDateRating().toString());

        RatingBar ratingRate = v.findViewById(R.id.ratingBarRateShop);
        ratingRate.setRating(rating.getRating());

        return v;
    }
}
