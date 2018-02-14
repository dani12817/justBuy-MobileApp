package danielleal.justbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import danielleal.justbuy.classes.listViewItems.adapterList.adapterAddress;
import danielleal.justbuy.classes.listViewItems.addressItemList;
import danielleal.justbuy.classes.userData;

public class addressList extends AppCompatActivity {

    adapterAddress adapter; addressList addressList;
    ArrayList<addressItemList> listItems = new ArrayList<>();
    ArrayList<Integer> listAddressID = new ArrayList<>();
    ListView listViewAddress;
    String userNick;
    sqlThread sqlThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        addressList = this;

        userNick = userData.getInstance().getNick();

        listViewAddress = findViewById(R.id.listAddresses);

        addAddressesToList();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //System.out.println(">>> resultCode = "+resultCode);
        if(resultCode==4) {
            //System.out.println("Hacer cosas");
            addAddressesToList();
        }
    }

    public void newAddress(View v){
        startActivityForResult(new Intent(addressList,editAddress.class),4);
    }

    public void addAddressesToList(){
        listItems.clear();
        listAddressID.clear();

        sqlThread = new sqlThread();
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new adapterAddress(this,listItems);

        listViewAddress.setAdapter(adapter);

        listViewAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                //System.out.println("Posicion "+position);
                startActivityForResult(new Intent(addressList,editAddress.class).putExtra("idAddress",listAddressID.get(position)),4);

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
                ResultSet rs = st.executeQuery("SELECT direccion.nombre,direccion.direccionLinea1,direccion.direccionLinea2,codpostal.numero,ciudad.nombre,provincia.nombre,direccion.iddireccion " +
                        "FROM direccion,codpostal,ciudad,provincia " +
                        "WHERE usuario = \"" + userNick + "\" AND codpostal.idcodPostal = direccion.codPostal AND codpostal.ciudad = ciudad.idciudad AND provincia.idprovincia = ciudad.provincia;");

                while(rs.next()) {
                    listItems.add(new addressItemList(rs.getString(1)
                            ,rs.getString(2)+", "+rs.getString(3)+"\n"+rs.getString(5)+", "+rs.getString(6)+" ("+rs.getString(4)+")"));
                    listAddressID.add(rs.getInt(7));
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
