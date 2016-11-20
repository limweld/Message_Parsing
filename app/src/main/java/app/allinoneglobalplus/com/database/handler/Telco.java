package app.allinoneglobalplus.com.database.handler;

/**
 * Created by ADMIN on 6/20/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 3/13/2016.
 */
public class Telco {
    private static String TBLNAME = "telco";

    private static String TELCONAME   = "telconame";
    public String telconame;

    public Telco() {}

    public Telco(String telconame) {

        this.telconame = telconame;
    }

    public void setTelconame(String telconame) {
        this.telconame = telconame;
    }

    public String getTelconame() {
        return telconame;
    }

    @Override
    public String toString() {
        return telconame;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();

        values.put(TELCONAME, telconame);
        try{
            DataHandler.db.insert(TBLNAME, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

    }

    public static Telco getStudent(String id)
    {
        Telco stud = null;
        Cursor cursor;
        try
        {
            cursor = DataHandler.db.query
                    (
                            TBLNAME,
                            new String[]{TELCONAME},
                            "telconame='" + id + "'",
                            null, null, null, null, null
                    );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    stud = new Telco();
                    //stud.id = cursor.getString(0);
                    stud.telconame = cursor.getString(0);
                    //  stud.lastname = cursor.getString(2);
                    // stud.course = cursor.getString(3);
                    //  stud.year = cursor.getString(4);
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
        return stud;
    }


    public List<String> getTelcoListSpinner()
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME,
                    new String[]{TELCONAME},
                    //"category_code='" + categorycode + "'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    dataArrays.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
        }
        catch (SQLException e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
        return dataArrays;
    }

}
