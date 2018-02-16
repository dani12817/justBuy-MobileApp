package danielleal.justbuy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

import danielleal.justbuy.classes.userData;

public class mainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView userLabel,nickLabel;
    ImageView userAvatar;
    EditText editZIPSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        editZIPSearch = findViewById(R.id.editSearchZip);

        NavigationView navigationView = findViewById(R.id.nav_view);
        nickLabel = navigationView.getHeaderView(0).findViewById(R.id.userDataLabel);
        userLabel = navigationView.getHeaderView(0).findViewById(R.id.userNameLabel);
        userAvatar = navigationView.getHeaderView(0).findViewById(R.id.userAvatar);

        changeMenuUserLabels();

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void changeMenuUserLabels(){
        if(userData.getInstance().getLogIn()){
            if(userData.getInstance().getAvatar() != null){
                userAvatar.setImageBitmap(
                        Bitmap.createScaledBitmap(
                                BitmapFactory.decodeByteArray(userData.getInstance().getAvatar(), 0, userData.getInstance().getAvatar().length),
                                221,
                                221,
                                false
                        )
                );
            }

            nickLabel.setText(userData.getInstance().getNick());
            userLabel.setText(userData.getInstance().getName());
        }else{
            userAvatar.setImageDrawable(getResources().getDrawable(R.drawable.no_avatar));
            nickLabel.setText(getResources().getString(R.string.userNameLabel));
            userLabel.setText("");
        }
    }

    public void searchShops(View V){

        System.out.println(">>> "+editZIPSearch.getText());

        if(editZIPSearch.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorSearchZIP), Toast.LENGTH_LONG).show();
        }else{
            startActivity(new Intent(this, searchShopResult.class).putExtra("searchShopZip", editZIPSearch.getText().toString()));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                userLabel.setText(userData.getInstance().getName());
                //System.out.println("Cambio Nombre");
                break;
            case 2:
                changeMenuUserLabels();
                //System.out.println("Cambio Total");
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            if(userData.getInstance().getLogIn()) {
                startActivityForResult(new Intent(this,userControlPanel.class),1);
            }else{
                startActivityForResult(new Intent(this, loginUser.class), 2);
            }
        } else if (id == R.id.nav_order) {
            startActivity(new Intent(this,orderList.class));
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(this,mainActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this,userControlPanel.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
