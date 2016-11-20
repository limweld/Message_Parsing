package app.allinoneglobalplus.com.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.allinoneglobalplus.com.R;
import app.allinoneglobalplus.com.database.handler.DataHandler;
import app.allinoneglobalplus.com.database.handler.Retailers;
import app.allinoneglobalplus.com.ui.businesses.BusinessListActivity;

/**
 * Created by ADMIN on 7/30/2016.
 */
public class Splash extends AppCompatActivity {
    private static DataHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        db = new DataHandler(this);

        /****** Create Thread that will sleep for 3 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(3*1000);

                    Retailers retailers = Retailers.getRetailersSession();
                    if(retailers != null){
                        if(retailers.getSession().contains("1"))
                        {
                            Intent intent = new Intent(Splash.this, BusinessListActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent i = new Intent(Splash.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }else{
                        Intent i = new Intent(Splash.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                    finish();

                } catch (Exception e) {

                }
            }
        };

        background.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
    }

}
