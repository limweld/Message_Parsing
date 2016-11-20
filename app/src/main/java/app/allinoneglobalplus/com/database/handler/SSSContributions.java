package app.allinoneglobalplus.com.database.handler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 6/29/2016.
 */
public class SSSContributions{
        private static String TBLNAME8 = "ssscontribution";
        private static String SSSCONTRIBUTIONS_ID = "_id";
        private static String SSSCONTRIBUTIONS_COMPANY_NAME = "company_name";
        private int id;
        private String company_name;

    public SSSContributions(){
    }


    public SSSContributions(int id, String company_name){
            this.id = id;
            this.company_name = company_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        @Override
        public String toString() {
            return company_name;
        }

        public void Insert()
        {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values.put("company_name", company_name);

            try{
                DataHandler.db.insert(TBLNAME8, null, values);
            }
            catch(Exception e)
            {
                Log.e("DB ERROR", e.toString());
                e.printStackTrace();
            }
        }

    public List<String> getSSSContributionListSpinner()
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try
        {
            cursor = DataHandler.db.query(
                    TBLNAME8,
                    new String[]{SSSCONTRIBUTIONS_COMPANY_NAME},
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
