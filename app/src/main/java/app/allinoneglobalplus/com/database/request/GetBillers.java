package app.allinoneglobalplus.com.database.request;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.allinoneglobalplus.com.database.handler.Billers;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Created by ADMIN on 7/8/2016.
 */
public class GetBillers extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public GetBillers(LoginActivity activity) {
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

            JSONArray articles = json.getJSONArray("billers");


            for(int i = 0; i<=articles.length(); i++) {

                String s2 = articles.getJSONObject(i).getString("id");
                String s3 = articles.getJSONObject(i).getString("category");
                Billers pro = new Billers(Integer.parseInt(s2),s3);
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
