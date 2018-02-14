package danielleal.justbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class userControlPanel extends AppCompatActivity {

    userControlPanel userControlPanelThis;
    boolean changedName = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_control_panel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userControlPanelThis = this;

        ((ListView) findViewById(R.id.listUserDetails)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                switch (position){
                    case 0:
                        startActivityForResult(new Intent(userControlPanelThis,editUserInfo.class),3);
                        break;
                    case 1:
                        startActivityForResult(new Intent(userControlPanelThis,addressList.class),3);
                        break;
                }
                //Log.i("Click", "click en el elemento " + position + " de mi ListView");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==3) {
            changedName = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onChangedName();
            //System.out.println("BACK");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onChangedName();
            //System.out.println("BACK");
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onChangedName(){
        if(changedName){
            Intent intent=new Intent();
            setResult(1,intent);
            this.finish();
        }else{
            this.finish();
        }
    }
}
