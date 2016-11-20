package app.allinoneglobalplus.com.database.request;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.allinoneglobalplus.com.database.handler.Telco;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Created by ADMIN on 6/23/2016.
 */
public class GetTelco extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public GetTelco(LoginActivity activity) {
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
        //String res = convertStandardJSONString(result);


        try {

            JSONObject json = new JSONObject(result); // convert String to JSONObject
            //  etResponse.setText(json.toString(1));

            JSONArray articles = json.getJSONArray("telco"); // get articles arra
            //  JSONArray articles2 = json.getJSONArray("telcoproducts");

            //int count = articles.length();
            for (int i = 0; i <= articles.length(); i++) {


                String s1 = articles.getJSONObject(i).getString("telconame");


                Telco t1 = new Telco(s1);
                t1.Insert();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }

}
