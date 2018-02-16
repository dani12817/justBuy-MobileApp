package danielleal.justbuy;

import android.content.Intent;
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

                startActivity(new Intent(searchShopResult,editAddress.class));

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
                            ,rs.getDouble(7),rs.getString(10),rs.getString(11),ZIPSearch));
                }

                conn.close();
            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            } catch (Throwable et) {
                System.out.println("oops! No se puede conectar. Error: " + et.toString());
            }
        }
    }
}
