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
public class Billers {
    private static String TBLNAME4 = "billercategory";
    private static String BILLERS_ID = "_id";
    private static String BILLERSCATEGORY = "category";
    public int id;
    public String category;

    public Billers() {}

    public Billers(int id,String category) {
        this.id= id;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() { return id; }

    public String getCategoty() { return category; }

    @Override
    public String toString() {
        return category;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();
        values.put(BILLERS_ID, id);
        values.put(BILLERSCATEGORY, category);
        try{
            DataHandler.db.insert(TBLNAME4, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

    }

    public List<String> getBillersListSpinner()
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME4,
                    new String[]{BILLERSCATEGORY},
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
