package app.allinoneglobalplus.com.database.request;

import android.app.ProgressDialog;
import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.allinoneglobalplus.com.database.handler.*;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Created by ADMIN on 6/23/2016.
 */
public class GetBillersProducts extends AsyncTask<String, Void, String> {

    private ProgressDialog dialog;

    public GetBillersProducts(LoginActivity activity) {
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Downloading ...");
        dialog.show();
    }


    @Override
    protected String doInBackground(String... urls) {

        return GetUrl.getUrl(urls[0]);

    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        try {

            JSONObject json = new JSONObject(result); // convert String to JSONObject

            JSONArray articles = json.getJSONArray("billersproducts");


            for(int i = 0; i<=articles.length(); i++) {

                String s2 = articles.getJSONObject(i).getString("id");
                String s3 = articles.getJSONObject(i).getString("categorycode");
                String s4 = articles.getJSONObject(i).getString("BillerTag");
                String s5 = articles.getJSONObject(i).getString("FirstField");
                String s6 = articles.getJSONObject(i).getString("SecondField");
                String s7 = articles.getJSONObject(i).getString("ServiceCharge");
                BillersProducts pro = new BillersProducts(Integer.parseInt(s2),s3,s4,s5,s6,s7);
                pro.Insert();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }

}
