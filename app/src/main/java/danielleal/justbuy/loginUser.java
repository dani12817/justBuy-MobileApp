package danielleal.justbuy;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import java.sql.*;

import danielleal.justbuy.classes.userData;

public class loginUser extends AppCompatActivity {
    String userNick,userPassword;
    sqlThread sqlThread;
    boolean logInState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
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

    public void loginUser(View V){
        userNick = ((EditText) findViewById(R.id.textUser)).getText().toString();
        userPassword = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        userPassword = new String(Hex.encodeHex(DigestUtils.sha1(userPassword)));

        sqlThread = new sqlThread();
        sqlThread.start();

        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(logInState){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.okUserLogIn), Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            setResult(2,intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorUserLogIn), Toast.LENGTH_LONG).show();
        }

    }

    public void registerUserActivity(View V){
        startActivity(new Intent(this,registerUser.class));
    }

    /*******************************************/

    public class sqlThread extends Thread{
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/justbuy", "root", "root");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE nick = \""+userNick+"\" AND clave = \""+userPassword+"\";");
                logInState = rs.next();

                if(logInState) {
                    userData.getInstance().logInSet(rs.getString(1),rs.getString(4),rs.getBytes(3),rs.getString(5),rs.getString(6));
                    userData.getInstance().setLogIn(true);
                    //System.out.println("Log In"+userData.getInstance().getNick());
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
