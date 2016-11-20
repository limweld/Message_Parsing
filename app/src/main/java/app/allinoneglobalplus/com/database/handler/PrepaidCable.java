package app.allinoneglobalplus.com.database.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 6/28/2016.
 */
public class PrepaidCable {
    private static String TBLNAME6 = "prepaidcable";
    private static String PREPAIDCABLE_ID = "_id";
    private static String PREPAIDCABLE_TYPE = "code";
    private int id;
    private String code;

    public PrepaidCable(){

    }

    public PrepaidCable(int id, String code){
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString(){
        return code;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();
        values.put(PREPAIDCABLE_ID, id);
        values.put(PREPAIDCABLE_TYPE, code);

        try{
            DataHandler.db.insert(TBLNAME6, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public List<String> getPrepaidCableListSpinnerById()
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME6,
                    new String[]{PREPAIDCABLE_TYPE},
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
