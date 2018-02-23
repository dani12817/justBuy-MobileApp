package danielleal.justbuy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import danielleal.justbuy.classes.listViewItems.adapterList.adapterProductShop;
import danielleal.justbuy.classes.listViewItems.adapterList.adapterRatingShop;
import danielleal.justbuy.classes.listViewItems.ratingShopItemList;
import danielleal.justbuy.classes.listViewItems.productShopItemList;
import danielleal.justbuy.classes.shopData;

public class shopSelected extends AppCompatActivity {

    private shopData shopData;
    private TextView shopName,shopAddress,shopCityZIP,shopDescription,shopCategory,shopNumRating;
    private RatingBar ratingAVG;
    private ImageView shopLogo;
    private sqlThread sqlThread;

    private sqlThreadProduct sqlThreadProduct;
    ArrayList<productShopItemList> listItemsShopProduct = new ArrayList<>();
    ListView listViewProductsShop,listViewRatingShop;
    adapterProductShop adapterProduct;

    sqlThreadRating sqlThreadRating;
    ArrayList<ratingShopItemList> listItemsShopRating = new ArrayList<>();
    adapterRatingShop adapterRating;

    private TextView labelAverageRating,labelBasedInCommentaries;
    private RatingBar ratingBarSearchShop;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeVisibililtyMenu(View.VISIBLE,View.GONE,View.GONE);
                    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.title_home), Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_notifications:
                    changeVisibililtyMenu(View.GONE,View.VISIBLE,View.GONE);
                    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.title_notifications), Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_dashboard://Product
                    changeVisibililtyMenu(View.GONE,View.GONE,View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.title_dashboard), Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_selected);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /************************/
        shopLogo = findViewById(R.id.logoShop);
        shopName = findViewById(R.id.labelShopName);
        shopAddress = findViewById(R.id.labelShopAddress);
        shopCityZIP = findViewById(R.id.labelShopCityZIP);
        shopDescription = findViewById(R.id.labelDescriptionShop);
        shopCategory = findViewById(R.id.labelShopCategory);
        shopNumRating = findViewById(R.id.labelSearchShopNumRatings);
        ratingAVG = findViewById(R.id.ratingBarSearchShop);

        labelAverageRating = findViewById(R.id.labelAverageRating);
        labelBasedInCommentaries = findViewById(R.id.labelBasedInCbasedInCommentaries);
        ratingBarSearchShop = findViewById(R.id.ratingBarSearchShop2);
        /************************/

        shopData = getIntent().getParcelableExtra("shopSelected");
        shopData.setLogo(getIntent().getByteArrayExtra("shopSelectedLogo"));

        //System.out.println(">>>>>>> "+shopData);

        listViewProductsShop = findViewById(R.id.listViewProductShop);
        addProductsToList();

        listViewRatingShop = findViewById(R.id.listRatingShop);
        addRatingToList();

        setActivityData();

        setTitle(shopData.getName());

    }

    public void changeVisibililtyMenu(int info, int rating, int product){
        findViewById(R.id.includeShopSelected).setVisibility(info);
        findViewById(R.id.includeShopSelectedRating).setVisibility(rating);
        findViewById(R.id.includeShopSelectedProduct).setVisibility(product);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActivityData(){


        if(shopData.getLogo() != null) {
            shopLogo.setImageBitmap(
                    Bitmap.createScaledBitmap(
                            BitmapFactory.decodeByteArray(shopData.getLogo(), 0, shopData.getLogo().length),
                            221,
                            221,
                            false
                    )
            );
        }

        shopName.setText(shopData.getName());
        shopAddress.setText(shopData.getAddressLine1()+", "+shopData.getAddressLine2());
        setCiudadTextView();
        shopDescription.setText(shopData.getDescription());
        shopCategory.setText(shopData.getCategories());
        shopNumRating.setText("("+shopData.getRatingNum()+")");
        ratingAVG.setRating(shopData.getRatingAVG());

        labelAverageRating.setText(String.format("%.1f", shopData.getRatingAVG()));
        labelBasedInCommentaries.setText(getResources().getString(R.string.basedInCommentaries).replace("x",shopData.getRatingNum()+""));
        ratingBarSearchShop.setRating(shopData.getRatingAVG());
    }

    public void setCiudadTextView(){

        sqlThread = new sqlThread();
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addProductsToList(){
        listItemsShopProduct.clear();

        sqlThreadProduct = new sqlThreadProduct();
        sqlThreadProduct.start();

        try {
            sqlThreadProduct.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapterProduct = new adapterProductShop(this,listItemsShopProduct);

        listViewProductsShop.setAdapter(adapterProduct);

        listViewProductsShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

            }
        });

    }

    public void addRatingToList(){
        listItemsShopRating.clear();

        sqlThreadRating = new sqlThreadRating();
        sqlThreadRating.start();

        try {
            sqlThreadRating.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapterRating = new adapterRatingShop(this,listItemsShopRating);

        listViewRatingShop.setAdapter(adapterRating);

        listViewRatingShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

            }
        });

    }

    /*******************************************/

    public class sqlThread extends Thread{
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT ciu.nombre, pro.nombre FROM codpostal cod, ciudad ciu, provincia pro " +
                        "WHERE cod.ciudad = ciu.idciudad AND pro.idprovincia = ciu.provincia AND " +
                        "cod.numero = \""+shopData.getZIP()+"\";");

                rs.next();

                shopCityZIP.setText(rs.getString(1)+", "+rs.getString(2)+" ("+shopData.getZIP()+")");

                conn.close();
            } catch (Throwable et) {
                System.out.println("oops! No se puede conectar. Error: " + et.toString());
            }
        }
    }

    /*******************************************/

    public class sqlThreadProduct extends Thread{
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT p.idproducto,p.imagen,p.nombre,p.descripcion,c.nombre,p.precio " +
                        "FROM Producto p, Categoria c " +
                        "WHERE tienda = "+shopData.getIdShop()+" AND p.categoria = c.idcategoria ORDER BY categoria;");

                while(rs.next()){
                    listItemsShopProduct.add(new productShopItemList(rs.getInt(1),rs.getBytes(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6)));
                }

                //System.out.println(">>>> Lista "+listItemsShopProduct.size());

                /*shopCityZIP.setText(rs.getString(1)+", "+rs.getString(2)+" ("+shopData.getZIP()+")");*/

                conn.close();
            } catch (Throwable et) {
                System.out.println("oops! No se puede conectar. Error: " + et.toString());
            }
        }
    }

    /*******************************************/

    public class sqlThreadRating extends Thread{
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM valoracion WHERE tienda = "+shopData.getIdShop()+";");

                while(rs.next()){
                    listItemsShopRating.add(new ratingShopItemList(rs.getString(2),rs.getFloat(3),rs.getString(4),rs.getDate(5)));
                }

                conn.close();
            } catch (Throwable et) {
                System.out.println("oops! No se puede conectar. Error: " + et.toString());
            }
        }
    }

}
