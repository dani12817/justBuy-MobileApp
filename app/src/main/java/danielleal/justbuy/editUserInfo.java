package danielleal.justbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import danielleal.justbuy.classes.userData;

public class editUserInfo extends AppCompatActivity {
    String sqlChanges ="";
    EditText textName,textEmail,textPhoneNumber;
    userData userData;
    sqlThread sqlThread;
    int resultCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userData = userData.getInstance();


        textName =findViewById(R.id.textName);
        textName.setText(userData.getName());

        textEmail = findViewById(R.id.textEmail);
        textEmail.setText(userData.getEmail());

        textPhoneNumber = findViewById(R.id.textPhoneNumber);
        textPhoneNumber.setText(userData.getPhone());

    }

    public void saveUserChanges(View V){

        prepareChanges();

        if(!sqlChanges.isEmpty()){

            sqlThread = new sqlThread();
            sqlThread.start();

            try {
                sqlThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.changesSaved), Toast.LENGTH_LONG).show();

            Intent intent=new Intent();
            setResult(resultCode,intent);
            //this.finish();

        }//else{
            //this.finish();
        //}

        this.finish();

    }

    public void prepareChanges(){

        if(!userData.getName().equals(textName.getText().toString())){
            sqlChanges += "nombre = \""+textName.getText()+"\"";
            userData.setName(textName.getText().toString());
            resultCode=3;
        }

        if(!userData.getEmail().equals(textEmail.getText().toString())){
            if(!sqlChanges.isEmpty()){
                sqlChanges += " AND ";
            }
            sqlChanges += "telefono = \""+textEmail.getText()+"\"";
            userData.setEmail(textEmail.getText().toString());
        }

        if(!userData.getPhone().equals(textPhoneNumber.getText().toString())){
            if(!sqlChanges.isEmpty()){
                sqlChanges += " AND ";
            }
            sqlChanges += "mail = \""+textPhoneNumber.getText()+"\"";
            userData.setPhone(textPhoneNumber.getText().toString());
        }

        //System.out.println("Changes -> "+sqlChanges);

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
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
                Statement st = conn.createStatement();
                st.execute("UPDATE usuario SET "+sqlChanges+" WHERE nick = \""+userData.getNick()+"\";");

                conn.close();
            } catch (SQLException se) {
                System.out.println("oops! SQLException. Error: " + se.toString());
            } catch (Throwable et) {
                System.out.println("oops! Throwable. Error: " + et.toString());
            }
        }
    }
}
