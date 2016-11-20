package app.allinoneglobalplus.com.database.handler;

/**
 * Created by ADMIN on 6/20/2016.
 */
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * Created by acer on 3/13/2016.
 */
public class Gateway
{
    private static String TBLNAME3 = "gateway";
    private static String GATEWAYDESCRIPTION = "description";
    private static String GATEWAYNUMBER = "number";
    public String description;
    public String number;

    public Gateway( String description, String number) {
        this.description = description;
        this.number = number;
    }

    public Gateway() {}



    @Override
    public String toString() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public String getNumber() {
        return number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void Insert()
    {
        ContentValues values = new ContentValues();

        values.put("description", description);
        values.put("number", number);

        try{
            DataHandler.db.insert(TBLNAME3, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

    }


//    public void update(String num, String des) {
//
//
//        ContentValues values = new ContentValues();
//        values.put("number", num);
//        values.put("description",des);
//
//        try {
//
//            DataHandler.db.update(TBLNAME3, values, "id=" + 1, null);
//
//            //DataHandler.db.update(TBLNAME3, values1, "id=" + this.id, null);
//
//        } catch (Exception e) {
//            Log.e("DB Error", e.toString());
//            e.printStackTrace();
//        }
//    }

    public static Gateway getGatewayNumber(String description)
    {
        Gateway gateway = null;
        Cursor cursor;
        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME3,
                    new String[]{GATEWAYDESCRIPTION,GATEWAYNUMBER},
                    GATEWAYDESCRIPTION + "='" + description +"'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    gateway = new Gateway();
                    gateway.description = cursor.getString(0);
                    gateway.number = cursor.getString(1);

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
        return gateway;
    }


}
