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
import danielleal.justbuy.classes.listViewItems.productShopItemList;

/**
 * Created by Dani on 21/02/2018.
 */

public class adapterProductShop extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<productShopItemList> items;

    public adapterProductShop (Activity activity, ArrayList<productShopItemList> items) {
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

    public void addAll(ArrayList<productShopItemList> shopsSearched) {
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
            v = inf.inflate(R.layout.list_item_product_shop, null);
        }

        productShopItemList product = items.get(position);

        TextView title = v.findViewById(R.id.labelProductName);
        //System.out.println(">>> Title "+title);
        title.setText(product.getNombre());

        if(product.getImagen() != null) {
            ImageView imagelogo = v.findViewById(R.id.imageProductLogo);
            imagelogo.setImageBitmap(
                    Bitmap.createScaledBitmap(
                            BitmapFactory.decodeByteArray(product.getImagen(), 0, product.getImagen().length),
                            221,
                            221,
                            false
                    )
            );
        }

        TextView category = v.findViewById(R.id.labelProdcutCategory);
        category.setText(product.getCategoria());

        TextView price = v.findViewById(R.id.labelPriceProduct);
        price.setText(product.getPrecio()+" €");

        return v;
    }
}
