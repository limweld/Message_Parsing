package app.allinoneglobalplus.com.database.request;

import android.app.ProgressDialog;
import android.os.AsyncTask;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.allinoneglobalplus.com.database.handler.*;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Created by ADMIN on 6/28/2016.
 */
public class GetPrepaidCableAmounts extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public GetPrepaidCableAmounts(LoginActivity activity) {
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

    protected void onPostExecute(String result) {
        try {

            JSONObject json = new JSONObject(result); // convert String to JSONObject

            JSONArray articles = json.getJSONArray("prepaidcableamounts");


            for(int i = 0; i<=articles.length(); i++) {

                int s2 = Integer.parseInt(articles.getJSONObject(i).getString("id"));
                String s3 = articles.getJSONObject(i).getString("type");
                String s4 = articles.getJSONObject(i).getString("amount");

                PrepaidCableAmounts pro = new PrepaidCableAmounts(s2,s3,s4);
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
