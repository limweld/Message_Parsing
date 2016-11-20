package app.allinoneglobalplus.com.database.request;

/**
 * Created by ADMIN on 6/23/2016.
 */

import android.app.Activity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class GetUrl extends Activity {

    private static String IP_ADDRESS = "null";
    private static String LOCAL_IP_ADDRESS = "null";
    private static String SOURCE_FOLDER = "null";

    public static String getIpAddress() {
        return IP_ADDRESS;
    }

    public static String getLocalIpAddress() { return LOCAL_IP_ADDRESS; }

    public static String getSourceFolder() {
        return SOURCE_FOLDER;
    }

    public static String getUrl(String url) {
        InputStream inputStream = null;
        String result = "";
        try {


            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



}


