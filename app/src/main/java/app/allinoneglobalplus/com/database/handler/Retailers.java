package app.allinoneglobalplus.com.database.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * Created by ADMIN on 7/11/2016.
 */
public class Retailers {
    private static String TBLNAME10 = "retailers";
    private static String RETAILERS_WALLET_ID = "wallet_id";
    private static String RETAILERS_PASSWORD = "password";
    private static String RETAILERS_SESSION = "session";
    private static String RETAILERS_TYPE = "type";

    private String wallet_id;
    private String password;
    private String type;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    private String session;

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Retailers(){


    }

    public Retailers(String wallet_id,
                     String password,
                     String type,
                     String session){
        this.session = session;
        this.wallet_id = wallet_id;
        this.password = password;
        this.type = type;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();

        values.put(RETAILERS_WALLET_ID, wallet_id);
        values.put(RETAILERS_PASSWORD, password);
        values.put(RETAILERS_TYPE, type);
        values.put(RETAILERS_SESSION, session);

        try{
            DataHandler.db.insert(TBLNAME10, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

        public void updateSession(String wallet_id, String password, String session) {


        ContentValues values = new ContentValues();
        values.put(RETAILERS_SESSION, session);

        try {

            DataHandler.db.update(
                    TBLNAME10,
                    values,
                    RETAILERS_WALLET_ID + "='" + wallet_id +"' AND " + RETAILERS_PASSWORD + "='" + password + "' AND " + RETAILERS_SESSION + "='1'",
                    //RETAILERS_SESSION + "='1'",
                    null
            );

        } catch (Exception e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }

    public void updateSessionByWallet_id(String wallet_id) {

        ContentValues values = new ContentValues();
        values.put(RETAILERS_SESSION, "1");

        try {

            DataHandler.db.update(
                    TBLNAME10,
                    values,
                    RETAILERS_WALLET_ID + "='" + wallet_id +"'",
                    null
            );

        } catch (Exception e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }

    public void updateSessionActive(String session) {

        ContentValues values = new ContentValues();
        values.put(RETAILERS_SESSION, session);

        try {

            DataHandler.db.update(
                    TBLNAME10,
                    values,
                    RETAILERS_SESSION + "='1'",
                    null
            );

        } catch (Exception e) {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }




    public static Retailers getRetailerByWalletIdPassword(String wallet_id, String password)
    {
        Retailers retailer = null;
        Cursor cursor;
        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME10,
                    new String[]{RETAILERS_WALLET_ID,RETAILERS_PASSWORD,RETAILERS_TYPE,RETAILERS_SESSION},
                    RETAILERS_WALLET_ID + "='" + wallet_id + "' AND "+ RETAILERS_PASSWORD +"='" + password + "'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    retailer = new Retailers();
                    retailer.wallet_id = cursor.getString(0);
                    retailer.password = cursor.getString(1);
                    retailer.type = cursor.getString(2);
                    retailer.session = cursor.getString(3);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
        return retailer;
    }

    public static Retailers getRetailersSession(){
        Retailers retailers = null;
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME10,
                    new String[]{RETAILERS_WALLET_ID,RETAILERS_PASSWORD,RETAILERS_TYPE,RETAILERS_SESSION},
                    RETAILERS_SESSION + "='1'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    retailers = new Retailers();
                    retailers.wallet_id = cursor.getString(0);
                    retailers.password = cursor.getString(1);
                    retailers.type = cursor.getString(2);
                    retailers.session = cursor.getString(3);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return  retailers;

    }
}
