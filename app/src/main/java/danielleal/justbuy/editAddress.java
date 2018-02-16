package danielleal.justbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import danielleal.justbuy.classes.ZIPItem;
import danielleal.justbuy.classes.addressItem;
import danielleal.justbuy.classes.spinAdapterZIP;
import danielleal.justbuy.classes.userData;

public class editAddress extends AppCompatActivity {
    int idAddressEdit;
    addressItem addressItemEdit;
    sqlThread sqlThread; int threadTask;
    EditText textNameAddress,textLine1Address,textLine2Address;
    Spinner spinnerListZIP;
    ArrayList<ZIPItem> ZIPList;
    String nickUser;
    //ArrayList<String> ZIPList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /********************/
        textNameAddress = findViewById(R.id.editNameAddress);
        textLine1Address = findViewById(R.id.editAddress1);
        textLine2Address = findViewById(R.id.editAddress2);
        spinnerListZIP = findViewById(R.id.spinnerListZIP);
        /*******************/

        nickUser = userData.getInstance().getNick();

        prepareZIPList();

        idAddressEdit = getIntent().getIntExtra("idAddress",-1);

        System.out.println("IdAddress -> "+idAddressEdit);

        if(idAddressEdit != -1){
            setTitle(getResources().getString(R.string.editAddress));
            prepareAddressItemEdit();
        }else{
            ((Button) findViewById(R.id.buttonDeleteAddress)).setVisibility(View.GONE);
        }

    }

    public void prepareAddressItemEdit(){
        sqlThread = new sqlThread();
        threadTask = 0;
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textNameAddress.setText(addressItemEdit.getName());
        textLine1Address.setText(addressItemEdit.getAddressLine1());
        textLine2Address.setText(addressItemEdit.getAddressLine2());

    }

    public void prepareZIPList(){
        sqlThread = new sqlThread();
        threadTask = 1;
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, ZIPList1);
        spinAdapterZIP adapter = new spinAdapterZIP(this,android.R.layout.simple_spinner_item,ZIPList);
        spinnerListZIP.setAdapter(adapter);
        spinnerListZIP.setSelection(0);

    }

    public void setAddressItemEdit(View v){
        sqlThread = new sqlThread();
        threadTask = 2;
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        setResult(4,intent);
        this.finish();
    }

    public void deleteAddressItemEdit(View v){
        sqlThread = new sqlThread();
        threadTask = 3;
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        setResult(4,intent);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){

            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    /*******************************************/

    public class sqlThread extends Thread{
        Connection conn;
        public void run(){
            try {
                switch (threadTask){
                    case 0:
                        getAddress();
                        break;
                    case 1:
                        getZIP();
                        break;
                    case 2:
                        setAddress();
                        break;
                    case 3:
                        deleteAddress();
                        break;
                }
            } catch (Throwable et) {
                System.out.println("oops! No se puede conectar. Error: " + et.toString());
            }
        }

        public void getAddress() throws Throwable{

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM direccion WHERE iddireccion = "+idAddressEdit);

            while(rs.next()) {
                addressItemEdit = new addressItem(rs.getInt(1),rs.getInt(6),rs.getString(4),rs.getString(5),rs.getString(3));
            }

            conn.close();
        }

        public void getZIP() throws Throwable{
            ZIPList = new ArrayList<>();

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM codpostal");

            while(rs.next()) {
                ZIPList.add(new ZIPItem(rs.getInt(1),rs.getString(2)));
            }

            conn.close();
        }

        public void setAddress() throws Throwable{
            String querySQL;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
            Statement st = conn.createStatement();
            if(idAddressEdit == -1) {
                querySQL = "INSERT INTO direccion(usuario,nombre,direccionLinea1,direccionLinea2,codPostal) " +
                        "VALUES(\"" + nickUser + "\",\"" + textNameAddress.getText() + "\",\"" + textLine1Address.getText() + "\",\"" + textLine2Address.getText() + "\"," +
                        +((ZIPItem) spinnerListZIP.getSelectedItem()).getIdZIP() + ");";
            }else{
                querySQL = "UPDATE direccion " +
                        "SET usuario = \""+nickUser+"\", nombre = \""+textNameAddress.getText()+"\", direccionLinea1 = \""+textLine1Address.getText()+"\", " +
                        "direccionLinea2 = \""+textLine2Address.getText()+"\", codPostal = "+((ZIPItem) spinnerListZIP.getSelectedItem()).getIdZIP()+" " +
                        "WHERE iddireccion = "+idAddressEdit+";";
            }

            //System.out.println("->>>>>> "+querySQL);
            st.execute(querySQL);

            conn.close();
        }

        public void deleteAddress() throws Throwable{
            String querySQL;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
            Statement st = conn.createStatement();
            querySQL = "DELETE FROM direccion WHERE iddireccion = "+idAddressEdit+";";

            st.execute(querySQL);

            conn.close();
        }
    }
}
