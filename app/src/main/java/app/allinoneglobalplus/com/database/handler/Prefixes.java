package app.allinoneglobalplus.com.database.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * Created by ADMIN on 7/20/2016.
 */
public class Prefixes {
    private static String TBLNAME11 = "prefixes";
    private static String PREFIX_ID = "_id";
    private static String PREFIX_SMART = "smart";
    private static String PREFIX_GLOBE = "globe";
    private static String PREFIX_SUN = "sun";

    private int id;
    private String smart;
    private String globe;
    private String sun;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmart() {
        return smart;
    }

    public void setSmart(String smart) {
        this.smart = smart;
    }

    public String getGlobe() {
        return globe;
    }

    public void setGlobe(String globe) {
        this.globe = globe;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public Prefixes()
    {

    }

    public Prefixes(int id, String smart, String globe, String sun)
    {
        this.id = id;
        this.globe = globe;
        this.sun = sun;
        this.smart = smart;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();
        values.put(PREFIX_ID, id);
        values.put(PREFIX_SMART, smart);
        values.put(PREFIX_GLOBE, globe);
        values.put(PREFIX_SUN, sun);

        try{
            DataHandler.db.insert(TBLNAME11, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public static Prefixes getPrefixesSun(){
        Prefixes prefixes = null;
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME11,
                    new String[]{PREFIX_SUN},
                    PREFIX_ID + "='1'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    prefixes = new Prefixes();
                    prefixes.sun = cursor.getString(0);

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

        return prefixes;

    }

    public static Prefixes getPrefixesGlobe(){
        Prefixes prefixes = null;
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME11,
                    new String[]{PREFIX_GLOBE},
                    PREFIX_ID + "='1'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    prefixes = new Prefixes();
                    prefixes.globe = cursor.getString(0);

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

        return prefixes;

    }

    public static Prefixes getPrefixesSmart(){
        Prefixes prefixes = null;
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME11,
                    new String[]{PREFIX_SMART},
                    PREFIX_ID + "='1'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    prefixes = new Prefixes();
                    prefixes.smart = cursor.getString(0);

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

        return prefixes;

    }



}
