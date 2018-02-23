package danielleal.justbuy;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import danielleal.justbuy.classes.listViewItems.adapterList.adapterSearchShop;
import danielleal.justbuy.classes.listViewItems.searchShopItemList;
import danielleal.justbuy.classes.shopData;

public class searchShopResult extends AppCompatActivity {
    String ZIPSearch; searchShopResult searchShopResult;
    adapterSearchShop adapter; sqlThread sqlThread;
    ListView listViewShops; TextView numShopsFound;
    ArrayList<searchShopItemList> listItemsShop = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_result);
        searchShopResult = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewShops = findViewById(R.id.listSearchResult);
        numShopsFound = findViewById(R.id.labelNumberShop);

        ZIPSearch = getIntent().getStringExtra("searchShopZip");

        setTitle(getResources().getString(R.string.shopResultZIP)+" "+ZIPSearch);

        addShopsToList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addShopsToList(){
        listItemsShop.clear();

        sqlThread = new sqlThread();
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        numShopsFound.setText(listItemsShop.size()+"");

        adapter = new adapterSearchShop(this,listItemsShop);

        listViewShops.setAdapter(adapter);

        listViewShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

                shopData shopData = new shopData(listItemsShop.get(pos));

                Intent intent = new Intent(searchShopResult,shopSelected.class).putExtra("shopSelected",shopData);
                intent.putExtra("shopSelectedLogo",listItemsShop.get(pos).getLogo());
                startActivity(intent);

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
                //System.out.println(">>>>>>>> SELECT * FROM tienda WHERE codPostal = (SELECT idcodPostal FROM codpostal WHERE numero = "+ZIPSearch+");");
                ResultSet rs = st.executeQuery("SELECT * FROM tienda WHERE codPostal = (SELECT idcodPostal FROM codpostal WHERE numero = "+ZIPSearch+");");

                while(rs.next()) {
                    listItemsShop.add(new searchShopItemList(rs.getInt(1),rs.getString(2),rs.getBytes(3),rs.getString(4),rs.getString(5),rs.getDouble(6)
                            ,rs.getDouble(7),rs.getString(11),rs.getString(12),ZIPSearch));
                }

                for (int i = 0; i<listItemsShop.size();i++){
                    rs = st.executeQuery("SELECT cat.nombre FROM categoriasdetienda catTie, categoria cat " +
                            "WHERE catTie.tienda = "+listItemsShop.get(i).getIdShop()+" AND catTie.categoria = cat.idcategoria;");
                    while(rs.next()) {
                        listItemsShop.get(i).setCategories(rs.getString(1));
                    }
                }

                for (int i = 0; i<listItemsShop.size();i++){
                    rs = st.executeQuery("SELECT COUNT(*),AVG(puntuacion) FROM justbuy.valoracion WHERE tienda = "+listItemsShop.get(i).getIdShop()+";");
                    rs.next();
                    listItemsShop.get(i).setRatingNum(rs.getInt(1));
                    listItemsShop.get(i).setRatingAverage(rs.getFloat(2));

                }

                rs.close();
                conn.close();
            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            } catch (Throwable et) {
                System.out.println("oops! No se puede conectar. Error: " + et.toString());
            }
        }
    }
}
