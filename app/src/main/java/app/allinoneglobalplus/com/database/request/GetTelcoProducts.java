package app.allinoneglobalplus.com.database.request;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.allinoneglobalplus.com.database.handler.TelcoProducts;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Created by ADMIN on 6/23/2016.
 */
public class GetTelcoProducts extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public GetTelcoProducts(LoginActivity activity) {
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
        //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        //   etResponse.setText(result);
        try {

            JSONObject json = new JSONObject(result); // convert String to JSONObject

            JSONArray articles = json.getJSONArray("telcopromo");


            for(int i = 0; i<=articles.length(); i++) {
                int s1 = Integer.parseInt(articles.getJSONObject(i).getString("id"));
                String s2 = articles.getJSONObject(i).getString("telconame");
                String s3 = articles.getJSONObject(i).getString("userkeyword");
                TelcoProducts pro = new TelcoProducts(s1,s2,s3);
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
