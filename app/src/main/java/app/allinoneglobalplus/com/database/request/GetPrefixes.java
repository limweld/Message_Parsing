package app.allinoneglobalplus.com.database.request;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.allinoneglobalplus.com.database.handler.Prefixes;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Created by ADMIN on 7/20/2016.
 */


public class GetPrefixes extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public GetPrefixes(LoginActivity activity) {
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

            JSONArray articles = json.getJSONArray("prefixes");

            for(int i = 0; i<=articles.length(); i++) {

                int s2 = Integer.parseInt(articles.getJSONObject(i).getString("id"));
                String s3 = articles.getJSONObject(i).getString("smart");
                String s4 = articles.getJSONObject(i).getString("globe");
                String s5 = articles.getJSONObject(i).getString("sun");

                Prefixes pro = new Prefixes(s2,s3,s4,s5);
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
