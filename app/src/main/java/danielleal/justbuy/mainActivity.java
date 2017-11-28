package danielleal.justbuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.sql.*;

public class mainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //if(userData.getInstance().getNick().isEmpty())userData.getInstance().setNick(getString(R.string.noRegister_label));

        ((EditText) findViewById(R.id.busquedaText)).setText("NADA");

        Log.i("Android", " Starting MySQL Connection");
        connectToDB();
    }

    public void connectToDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "dani", "1234");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from usuario");

            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
            }

            //tv.setText(result);

        } catch (Exception e) {
            System.out.println(">>> "+e.getMessage());
            //tv.setText("Test Error:"+e.toString());
            Log.w("Android-system","system get connection");
        }
    }

    public void enClick(View V){
        //((TextView) findViewById(R.id.labelBusqueda)).setText(((EditText) findViewById(R.id.busquedaText)).getText());
        Intent intent = new Intent(this, resultadoTiendas.class);
        intent.putExtra("searchZIP", ((EditText) findViewById(R.id.busquedaText)).getText());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_AZ:
                Log.i("Order","Alphabet Order");
                break;
            case R.id.action_rating:
                Log.i("Order","Rating Order");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            startActivity(new Intent(this,loginUser.class));
        } else if (id == R.id.nav_order) {
            startActivity(new Intent(this,orderList.class));
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(this,mainActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this,userControlPanel.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
