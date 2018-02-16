package danielleal.justbuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import danielleal.justbuy.classes.listViewItems.addressItemList;

public class registerUser extends AppCompatActivity {
    EditText editNick,editPassword,editPhone,editMail;
    sqlThread sqlThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        editNick = findViewById(R.id.textNickRegister);
        editPassword = findViewById(R.id.textPasswordRegister);
        editPhone = findViewById(R.id.textPhoneRegister);
        editMail = findViewById(R.id.textMailRegister);

        //Add Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void closeActivity(View view){
        this.finish();
    }

    public void registerUser(View view){
        sqlThread = new sqlThread();
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.successNewUser), Toast.LENGTH_LONG).show();

        closeActivity(view);
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

                st.execute("INSERT INTO usuario (nick,clave,avatar,nombre,telefono,email) " +
                        "VALUES(\""+editNick.getText()+"\",\""+new String(Hex.encodeHex(DigestUtils.sha1(editPassword.getText().toString())))+"\",null,\""+editNick.getText()+"\"," +
                        Integer.parseInt(editPhone.getText().toString())+",\""+editMail.getText()+"\");");

                conn.close();
            } catch (Throwable et) {
                System.out.println("oops! Error: " + et.toString());
            }
        }
    }
}
