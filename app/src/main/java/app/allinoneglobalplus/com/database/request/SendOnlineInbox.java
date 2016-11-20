package app.allinoneglobalplus.com.database.request;

import android.os.AsyncTask;

/**
 * Created by ADMIN on 7/29/2016.
 */
public class SendOnlineInbox extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... urls) {

        return GetUrl.getUrl(urls[0]);

    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

    }

}
