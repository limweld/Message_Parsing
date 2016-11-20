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
public class GetGateway  extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public GetGateway(LoginActivity activity) {
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
            //new HttpAsyncTask().execute("http://192.168.254.100:8080/eloadtest/get_telco.php");
            JSONObject json = new JSONObject(result); // convert String to JSONObject

            JSONArray articles = json.getJSONArray("gateway");


            for(int i = 0; i<=articles.length(); i++) {

                String s3 = articles.getJSONObject(i).getString("number");
                String s4 = articles.getJSONObject(i).getString("description");

                Gateway pro = new Gateway(s4,s3);
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
