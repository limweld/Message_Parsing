package app.allinoneglobalplus.com.ui.login;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;

import app.allinoneglobalplus.com.R;
import app.allinoneglobalplus.com.database.handler.DataHandler;
import app.allinoneglobalplus.com.database.handler.Retailers;
import app.allinoneglobalplus.com.database.request.GetBillers;
import app.allinoneglobalplus.com.database.request.GetBillersProducts;
import app.allinoneglobalplus.com.database.request.GetGateway;
import app.allinoneglobalplus.com.database.request.GetPhilHealthPremium;
import app.allinoneglobalplus.com.database.request.GetPrefixes;
import app.allinoneglobalplus.com.database.request.GetPrepaidCable;
import app.allinoneglobalplus.com.database.request.GetPrepaidCableAmounts;
import app.allinoneglobalplus.com.database.request.GetSSSContributions;
import app.allinoneglobalplus.com.database.request.GetTelco;
import app.allinoneglobalplus.com.database.request.GetTelcoProducts;
import app.allinoneglobalplus.com.database.request.GetUrl;
import app.allinoneglobalplus.com.ui.businesses.BusinessListActivity;
import app.allinoneglobalplus.com.ui.dialog.ErrorDialogFragment;

public class LoginActivity extends AppCompatActivity {

    SessionManager manager;
    //SharedPreferences sp;


    public static String getWallet_id() {
        return _walletid.getText().toString();
    }

    public static String getPassword() {
        return _password.getText().toString();
    }


    public static DataHandler db;
//    @Bind(R.id.editText_wallet_id) EditText _walletid;
//    @Bind(R.id.editText_password) EditText _password;
//    @Bind(R.id.btn_login) Button btn_login;

    private static EditText _walletid, _password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retailers retailers = Retailers.getRetailersSession();

        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        if(wifiManager.isWifiEnabled()==false)
        wifiManager.setWifiEnabled(true);

            try {
                if (retailers.getSession() == "2")
                    connect(isConnectedToServer());
            }catch(Exception ex){
                connect(isConnectedToServer());
            }



        _walletid = (EditText) findViewById(R.id.editText_wallet_id);
        _password = (EditText) findViewById(R.id.editText_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        _walletid.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            private boolean isFormatting;
            private boolean deletingHyphen;
            private int hyphenStart;
            private boolean deletingBackward;

            @Override
            public void afterTextChanged(Editable text) {
                if (isFormatting)
                    return;

                isFormatting = true;

                // If deleting hyphen, also delete character before or after it
                if (deletingHyphen && hyphenStart > 0) {
                    if (deletingBackward) {
                        if (hyphenStart - 1 < text.length()) {
                            text.delete(hyphenStart - 1, hyphenStart);
                        }
                    } else if (hyphenStart < text.length()) {
                        text.delete(hyphenStart, hyphenStart + 1);
                    }
                }
                if (text.length() == 4 || text.length() == 9) {
                    text.append('-');
                }

                isFormatting = false;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFormatting)
                    return;

                // Make sure user is deleting one char, without a selection
                final int selStart = Selection.getSelectionStart(s);
                final int selEnd = Selection.getSelectionEnd(s);
                if (s.length() > 1 // Can delete another character
                        && count == 1 // Deleting only one character
                        && after == 0 // Deleting
                        && s.charAt(start) == '-' // a hyphen
                        && selStart == selEnd) { // no selection
                    deletingHyphen = true;
                    hyphenStart = start;
                    // Check if the user is deleting forward or backward
                    if (selStart == start + 1) {
                        deletingBackward = true;
                    } else {
                        deletingBackward = false;
                    }
                } else {
                    deletingHyphen = false;
                }
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (_walletid.getText().toString().isEmpty() || _password.getText().toString().isEmpty()) {
                    validate();
                    return;
                } else {

                    try {

                        Retailers retailer = Retailers.getRetailerByWalletIdPassword(_walletid.getText().toString(), _password.getText().toString());

                        if (retailer != null) {


                            retailer.updateSessionByWallet_id(_walletid.getText().toString());
                            onLoginSuccess();
                            return;
                        } else {
                            try {
                                connect(_walletid.getText().toString(), _password.getText().toString(),isConnectedToServer());

                                return;
                            } catch (Exception ex) {

                            }
                        }
                    } catch (Exception ex) {

                    }

                }
                onLoginFailed();
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    public void onLoginSuccess() {

        Intent intent = new Intent(LoginActivity.this, BusinessListActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        showDialog("Network Error!");
    }

    public boolean validate() {
        boolean valid = true;

        String walletid = _walletid.getText().toString();
        String password = _password.getText().toString();

        if (walletid.isEmpty()) {
            _walletid.setError("Empty WalletID");
            valid = false;
        } else {
            _walletid.setError(null);
        }

        if (password.isEmpty()) {
            _password.setError("Empty Password");
            valid = false;
        } else {
            _password.setError(null);
        }

        return valid;
    }


    public void connect(String walletid, String password,String Url) {

        GetRetailers getRetailers = new GetRetailers(LoginActivity.this);
        getRetailers.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_retailer.php?walletid=" + walletid + "&password=" + password);
    }

    public void connect(String Url) {

        GetBillers getBillers = new GetBillers(LoginActivity.this);
        getBillers.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_billers.php");

        GetGateway getGateway = new GetGateway(LoginActivity.this);
        getGateway.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_gateway.php");

        GetBillersProducts getBillersProducts = new GetBillersProducts(LoginActivity.this);
        getBillersProducts.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_billersproducts.php");

        GetTelcoProducts getTelcoProducts = new GetTelcoProducts(LoginActivity.this);
        getTelcoProducts.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_telcoproducts.php");

        GetTelco getTelco = new GetTelco(LoginActivity.this);
        getTelco.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_telco.php");

        GetPrepaidCable getPrepaidCable = new GetPrepaidCable(LoginActivity.this);
        getPrepaidCable.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_prepaidcable.php");

        GetPrepaidCableAmounts getPrepaidCableAmounts = new GetPrepaidCableAmounts(LoginActivity.this);
        getPrepaidCableAmounts.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_prepaidcableamounts.php");

        GetSSSContributions getSSSContributions = new GetSSSContributions(LoginActivity.this);
        getSSSContributions.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_ssscontributions.php");

        GetPhilHealthPremium getPhilHealthPremium = new GetPhilHealthPremium(LoginActivity.this);
        getPhilHealthPremium.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_philhealthpremium.php");

        GetPrefixes getPrefixes = new GetPrefixes(LoginActivity.this);
        getPrefixes.execute("http://" + Url + "/" + GetUrl.getSourceFolder() + "/get_prefixes.php");

    }

    public class GetRetailers extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        public GetRetailers(LoginActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Login ...");
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

                JSONArray articles = json.getJSONArray("retailer");


                for(int i = 0; i<=articles.length(); i++) {

                    String s2 = articles.getJSONObject(i).getString("walletid");
                    String s3 = articles.getJSONObject(i).getString("password");
                    String s4 = articles.getJSONObject(i).getString("type");

                    Retailers pro = new Retailers(s2,s3,s4,"1");

                    pro.Insert();

                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                    onLoginSuccess();
                }
            } catch (JSONException e) {

                e.printStackTrace();
                onLoginFailed();

            }finally {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

        }

    }

    public void showDialog(String message){
        FragmentManager fm = getFragmentManager();
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        dialogFragment.show(fm, message);

    }

    public String isConnectedToServer() {
        int timeout = 5000;

        try{
            URL myUrl = new URL(GetUrl.getIpAddress());
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return GetUrl.getIpAddress();
        } catch (Exception e) {
            return GetUrl.getLocalIpAddress();
        }
    }

}
