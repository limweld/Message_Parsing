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
public class BillersProducts {
    private static String TBLNAME5 = "billers";
    private static String BILLERSPRODUCTS_ID = "_id";
    private static String BILLERSPRODUCTSCATEGORY = "category_code";
    private static String BILLERTAG = "billers_tag";
    private static String BILLERSTAG_FIRSTFIELD = "firstfield";
    private static String BILLERSTAG_SECONDFIELD = "secondfield";
    private static String SERVICECHARGE = "service_charge";
    private int id;
    private String categorycode;
    private String billerTag;
    private String serviceCharge;
    private String firstfield;
    private String secondfield;

    public String getFirstfield() {
        return firstfield;
    }

    public void setSecondfield(String secondfield) {
        this.secondfield = secondfield;
    }

    public BillersProducts() {

    }

    public BillersProducts(int id,
                            String categorycode,
                            String billerTag,
                            String firstfield,
                            String secondfield,
                            String serviceCharge
                           )
    {
        this.id = id;
        this.categorycode = categorycode;
        this.billerTag = billerTag;
        this.firstfield = firstfield;
        this.secondfield = secondfield;
        this.serviceCharge = serviceCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public String getBillerTag() {
        return billerTag;
    }

    public void setBillerTag(String billerTag) {
        this.billerTag = billerTag;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public void setFirstfield(String firstfield) {
        this.firstfield = firstfield;
    }

    public String getSecondfield() {
        return secondfield;
    }


    public void Insert()
    {
        ContentValues values = new ContentValues();
       
        values.put(BILLERSPRODUCTS_ID, id);
        values.put(BILLERSPRODUCTSCATEGORY, categorycode);
        values.put(BILLERTAG, billerTag);
        values.put(BILLERSTAG_FIRSTFIELD,firstfield);
        values.put(BILLERSTAG_SECONDFIELD,secondfield);
        values.put(SERVICECHARGE, serviceCharge);

        try{
            DataHandler.db.insert(TBLNAME5, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

    }

    public static BillersProducts getServiceChargeText(String tag)
    {
        BillersProducts billersProducts = null;
        Cursor cursor;
        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME5,
                    new String[]{BILLERSTAG_FIRSTFIELD,BILLERSTAG_SECONDFIELD,SERVICECHARGE},
                    BILLERTAG + "='" + tag + "'",
                    null, null, null, null, null
            );
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                do
                {
                    billersProducts = new BillersProducts();
                    billersProducts.firstfield = cursor.getString(0);
                    billersProducts.secondfield = cursor.getString(1);
                    billersProducts.serviceCharge = cursor.getString(2);

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
        return billersProducts;
    }

    public List<String> getBillerProductsListSpinnerById(String categorycode)
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME5,
                    new String[]{BILLERTAG},
                    BILLERSPRODUCTSCATEGORY + "='" + categorycode + "'",
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
