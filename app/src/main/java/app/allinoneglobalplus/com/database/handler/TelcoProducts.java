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
public class TelcoProducts {
    private static String TBLNAME2    = "telcopromo";
    private static String TELCOPRODUCTS_ID = "_id";
    private static String TELCOPRODUCTSNAME = "telconame";
    private static String USERKEYWORD = "userkeyword";
    int id;
    String telconame;
    String userkeyword;

    public TelcoProducts() {

    }

    public TelcoProducts(int id, String telconame, String userkeyword) {
        this.id = id;
        this.telconame = telconame;
        this.userkeyword = userkeyword;
    }

    public int getId() {
        return id;
    }

    public String getTelconame() {
        return telconame;
    }

    public String getUserkeyword() {
        return userkeyword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTelconame(String telconame) {
        this.telconame = telconame;
    }

    public void setUserkeyword(String userkeyword) {
        this.userkeyword = userkeyword;
    }

    @Override
    public String toString() {
        return  userkeyword;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();
        values.put(TELCOPRODUCTS_ID, id);
        values.put(TELCOPRODUCTSNAME, telconame);
        values.put(USERKEYWORD, userkeyword);
        try{
            DataHandler.db.insert(TBLNAME2, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public List<String> getTelcoProductsListSpinnerById(String telconame)
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME2,
                    new String[]{USERKEYWORD},
                    TELCOPRODUCTSNAME + "='" + telconame + "'",
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

