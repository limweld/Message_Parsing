package app.allinoneglobalplus.com.database.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limwel on 7/3/2016.
 */
public class PrepaidCableAmounts {
    private static String TBLNAME7 = "prepaidcableamounts";
    private static String PREPAIDCABLEAMOUNTS_ID = "_id";
    private static String PREPAIDCABLEAMOUNTS_TYPE = "type";
    private static String PREPAIDCABLEAMOUNTS_AMOUNT = "amount";
    private int id;
    private String type;
    private String amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public PrepaidCableAmounts(){ }

    public PrepaidCableAmounts(int id, String type, String amount){
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();
        values.put(PREPAIDCABLEAMOUNTS_ID, id);
        values.put(PREPAIDCABLEAMOUNTS_TYPE, type);
        values.put(PREPAIDCABLEAMOUNTS_AMOUNT, amount);

        try{
            DataHandler.db.insert(TBLNAME7, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public List<String> getPrepaidCableAmountsListSpinnerById(String type)
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME7,
                    new String[]{PREPAIDCABLEAMOUNTS_AMOUNT},
                    PREPAIDCABLEAMOUNTS_TYPE + "='" + type + "'",
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
