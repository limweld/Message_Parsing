package app.allinoneglobalplus.com.ui.businesses;

import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.allinoneglobalplus.com.R;
import app.allinoneglobalplus.com.database.handler.Billers;
import app.allinoneglobalplus.com.database.handler.BillersProducts;
import app.allinoneglobalplus.com.database.handler.DataHandler;
import app.allinoneglobalplus.com.database.handler.Gateway;
import app.allinoneglobalplus.com.database.handler.PhilHealthPremium;
import app.allinoneglobalplus.com.database.handler.Prefixes;
import app.allinoneglobalplus.com.database.handler.PrepaidCable;
import app.allinoneglobalplus.com.database.handler.PrepaidCableAmounts;
import app.allinoneglobalplus.com.database.handler.Retailers;
import app.allinoneglobalplus.com.database.handler.SSSContributions;
import app.allinoneglobalplus.com.database.handler.Telco;
import app.allinoneglobalplus.com.database.handler.TelcoProducts;
import app.allinoneglobalplus.com.database.request.GetUrl;
import app.allinoneglobalplus.com.database.request.SendOnlineInbox;
import app.allinoneglobalplus.com.ui.base.BaseActivity;
import app.allinoneglobalplus.com.ui.base.BaseFragment;
import app.allinoneglobalplus.com.ui.content.BusinessContent;
import app.allinoneglobalplus.com.ui.dialog.ConfirmationDialogFragment;
import app.allinoneglobalplus.com.ui.dialog.ErrorDialogFragment;
import app.allinoneglobalplus.com.ui.login.SessionManager;
import butterknife.Bind;
/**
 * Shows the quote detail page.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class BusinessDetailFragment extends BaseFragment {

    SessionManager manager;

    Spinner spinner_bills, spinner_telco, spinner_prepaidCable,
            spinner_subBills, spinner_subTelco, spinner_subPrepaidCable,
            spinner_sssContribution, spinner_philHealth;

    EditText  editText_boxNumber, editText_prepaidCableCutomer,
            editText_parameter1, editText_parameter2, editText_amountBill, editText_convinienceFee, editText_customersBill, editText_customersNumberBill, editText_totalBill,
            editText_eloadingCustomerNum,
            editText_sssMember, editText_sssMonthYear, editText_sssNumber, editText_sss_customersNumber, editText_sssAmount,
            editText_philH_pin, editText_philH_fname, editText_philH_mname, editText_philH_lname, editText_philH_from, editText_philH_to, editText_philH_amount, editText_philH_cell;

    TextView textView_parameter1, textView_parameter2;

    Button button_send;

    RadioButton radioButton_http, radioButton_sms;

    String mess, sender, sender0;

    DataHandler db = new DataHandler();

    /**
     * The argument represents the dummy item ID of this fragment.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content of this fragment.
     */
    private BusinessContent.DummyItem dummyItem;

    private Context context;
    private View view;

    @Bind(R.id.quote)
    TextView quote;

    @Bind(R.id.author)
    TextView author;

    @Bind(R.id.backdrop)
    ImageView backdropImg;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.linearLayout2)
    LinearLayout linearLay;

    public BusinessDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // load dummy item by using the passed item ID.
            dummyItem = BusinessContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_article_detail);

        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        View overriding = inflater.inflate(dummyItem.formContent, container, false);


        if (dummyItem != null) {
            loadBackdrop();
            collapsingToolbar.setTitle(dummyItem.title);
            author.setText(dummyItem.author);
            quote.setText(dummyItem.content);
            linearLay.addView(overriding);
        }

        listeners(rootView);


        TelcoSpinner();
        BillsSpinner();
        PrepaidCable();
        SSSContribution();
        PhilHealth();

        try {

            spinner_bills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selected = parentView.getItemAtPosition(position).toString();
                    SubBillersTagSpinner(Integer.toString(position + 1));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });

        } catch (Exception ex) {
        }

        try {

            spinner_telco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selected = spinner_telco.getSelectedItem().toString();
                    SubTelcoProductsSpinner(selected);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        } catch (Exception ex) {
        }

        try {
            spinner_prepaidCable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selected = spinner_prepaidCable.getSelectedItem().toString();
                    SubPrepaidCableSpinner(selected);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        } catch (Exception ex) {
        }

        try {
            spinner_subBills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        String selected = spinner_subBills.getSelectedItem().toString();
                        BillersProducts billersProducts = BillersProducts.getServiceChargeText(selected);

                        String list = billersProducts.getServiceCharge();
                        editText_convinienceFee.setText(list);
                        textView_parameter1.setText(billersProducts.getFirstfield());
                        textView_parameter2.setText(billersProducts.getSecondfield());


                    } catch (Exception ex) {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        } catch (Exception ex) {
        }


        button_send = (Button) rootView.findViewById(R.id.button_send);

        button_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Retailers retailers = Retailers.getRetailersSession();

                mess = MessageParse(dummyItem.id);

                String[] prefix = retailers.getWallet_id().split("-");

                sender0 = gatewayNumber(gatewayNetwork(prefix[0]));
                sender = prefix[0]+prefix[1]+prefix[2];

                if (radioButton_sms.isChecked()) {

                        if (validateType(dummyItem.id)) {
                            sendSMS(sender0, mess, dummyItem.id);
                        }
                }
                if (radioButton_http.isChecked()) {

                    if (validateType(dummyItem.id)) {
                        if(wifiConnected()==true){
                                new SendMessageHttp().execute((Void) null);
                            clear(dummyItem.id);
                        }
                    }

                }
            }
        });

        try {

            editText_amountBill.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {

                        float num = Float.parseFloat(editText_amountBill.getText().toString());
                        float num1 = Float.parseFloat(editText_convinienceFee.getText().toString());
                        float total = num + num1;
                        editText_totalBill.setText(Float.toString(total));

                    } catch (Exception ex) {

                    }

                }
            });

            editText_convinienceFee.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {
                        float num = Float.parseFloat(editText_amountBill.getText().toString());
                        float num1 = Float.parseFloat(editText_convinienceFee.getText().toString());
                        float total = num + num1;
                        editText_totalBill.setText(Float.toString(total));


                    } catch (Exception ex) {

                    }

                }
            });


        } catch (Exception ex) {

        }


        return rootView;
    }

    //@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                break;

        }
    }


    public void listeners(View rootView) {
        spinner_subBills = (Spinner) rootView.findViewById(R.id.spinner_BillersTypeName);
        spinner_subTelco = (Spinner) rootView.findViewById(R.id.spinner_TelcoProducts);
        spinner_subPrepaidCable = (Spinner) rootView.findViewById(R.id.spinner_CableAmount);
        spinner_telco = (Spinner) rootView.findViewById(R.id.spinner_TelcoName);
        spinner_bills = (Spinner) rootView.findViewById(R.id.spinner_BillersCategory);
        spinner_prepaidCable = (Spinner) rootView.findViewById(R.id.spinner_CableType);
        spinner_sssContribution = (Spinner) rootView.findViewById(R.id.spinner_SSSContributions);
        spinner_philHealth = (Spinner) rootView.findViewById(R.id.spinner_PhilHealthPremium);

        editText_boxNumber = (EditText) rootView.findViewById(R.id.editText_boxNumber);
        editText_prepaidCableCutomer = (EditText) rootView.findViewById(R.id.editText_prepaidCableCustomer);

        editText_parameter1 = (EditText) rootView.findViewById(R.id.editText_paybill_parameter1);
        editText_parameter2 = (EditText) rootView.findViewById(R.id.editText_paybill_parameter2);
        editText_amountBill = (EditText) rootView.findViewById(R.id.editText_paybill_amount);
        editText_convinienceFee = (EditText) rootView.findViewById(R.id.editText_paybill_convinienceFee);
        editText_customersBill = (EditText) rootView.findViewById(R.id.editText_paybill_CustomerName);
        editText_customersNumberBill = (EditText) rootView.findViewById(R.id.editText_paybill_customerCellNumber);
        editText_totalBill = (EditText) rootView.findViewById(R.id.editText_paybill_totalbill);

        editText_eloadingCustomerNum = (EditText) rootView.findViewById(R.id.editText_eloadCustomer);

        editText_sssMember = (EditText) rootView.findViewById(R.id.editText_sss_memberName);
        editText_sssMonthYear = (EditText) rootView.findViewById(R.id.editText_sss_monthYear);
        editText_sssNumber = (EditText) rootView.findViewById(R.id.editText_sss_numberPin);
        editText_sss_customersNumber = (EditText) rootView.findViewById(R.id.editText_sss_CustomersNumber);
        editText_sssAmount = (EditText) rootView.findViewById(R.id.editText__sss_amount);

        editText_philH_pin = (EditText) rootView.findViewById(R.id.editText_philH_pin);
        editText_philH_fname = (EditText) rootView.findViewById(R.id.editText_philH_firstname);
        editText_philH_mname = (EditText) rootView.findViewById(R.id.editText_philH_middlename);
        editText_philH_lname = (EditText) rootView.findViewById(R.id.editText_philH_lastname);
        editText_philH_from = (EditText) rootView.findViewById(R.id.editText_philH_periodFrom);
        editText_philH_to = (EditText) rootView.findViewById(R.id.editText_philH_periodTo);
        editText_philH_amount = (EditText) rootView.findViewById(R.id.editText_philH_amount);
        editText_philH_cell = (EditText) rootView.findViewById(R.id.editText_philH_CustomerNum);

        textView_parameter1 = (TextView) rootView.findViewById(R.id.textView_parameter1);
        textView_parameter2 = (TextView) rootView.findViewById(R.id.textView_parameter2);

        button_send = (Button) rootView.findViewById(R.id.button_send);

        radioButton_http = (RadioButton) rootView.findViewById(R.id.radioButton_http);
        radioButton_sms = (RadioButton) rootView.findViewById(R.id.radioButton_sms);
    }

    private void loadBackdrop() {
        Glide.with(this).load(dummyItem.photoId).centerCrop().into(backdropImg);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.sample_actions, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                try {
//                    manager.setPreferences(getActivity(), "status", "0");
//                    getActivity().finish();
//                } catch (Exception e) {
//
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public static BusinessDetailFragment newInstance(String itemID) {
        BusinessDetailFragment fragment = new BusinessDetailFragment();
        Bundle args = new Bundle();
        args.putString(BusinessDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public void BillsSpinner() {
        try {

            Billers billersProducts = new Billers();
            List<String> list = billersProducts.getBillersListSpinner();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_bills.setAdapter(dataAdapter);


        } catch (Exception e) {

        }
    }

    public void TelcoSpinner() {
        try {

            Telco telco = new Telco();
            List<String> list = telco.getTelcoListSpinner();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_telco.setAdapter(dataAdapter);

        } catch (Exception e) {

        }
    }

    public void PrepaidCable() {
        try {

            PrepaidCable prepaidCable = new PrepaidCable();
            List<String> list = prepaidCable.getPrepaidCableListSpinnerById();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_prepaidCable.setAdapter(dataAdapter);

        } catch (Exception e) {

        }
    }


    public void SubBillersTagSpinner(String position) {
        try {

            BillersProducts billersProducts = new BillersProducts();
            List<String> list = billersProducts.getBillerProductsListSpinnerById(position);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_subBills.setAdapter(dataAdapter);

        } catch (Exception e) {

        }

    }


    public void SubTelcoProductsSpinner(String telconame) {
        try {

            TelcoProducts telcoProducts = new TelcoProducts();
            List<String> list = telcoProducts.getTelcoProductsListSpinnerById(telconame);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_subTelco.setAdapter(dataAdapter);

        } catch (Exception e) {

        }
    }


    public void SubPrepaidCableSpinner(String cable) {
        try {

            PrepaidCableAmounts prepaidCableAmounts = new PrepaidCableAmounts();
            List<String> list = prepaidCableAmounts.getPrepaidCableAmountsListSpinnerById(cable);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_subPrepaidCable.setAdapter(dataAdapter);


        } catch (Exception e) {

        }
    }

    public void SSSContribution() {
        try {

            SSSContributions sSSContributions = new SSSContributions();
            List<String> list = sSSContributions.getSSSContributionListSpinner();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_sssContribution.setAdapter(dataAdapter);

        } catch (Exception e) {

        }
    }

    public void PhilHealth() {
        try {

            PhilHealthPremium philHealthPremium = new PhilHealthPremium();
            List<String> list = philHealthPremium.getPhilHealthPremiumListSpinner();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_philHealth.setAdapter(dataAdapter);

        } catch (Exception e) {

        }
    }

    public Boolean validateType(String type){
        boolean valid = true;

        if (type == "1") {
            return validateLoading();
        }

        if (type == "2") {
            return validateBillsPayment();
        }

        if (type == "3") {
            return validatePrepaidCable();
        }

        return valid;
    }

    public String MessageParse(String type) {


        if (type == "1") {
            String elodingCustomerNum = editText_eloadingCustomerNum.getText().toString();
            String telcoProduct = spinner_subTelco.getSelectedItem().toString();

            return (elodingCustomerNum + " " + telcoProduct);
        }
        if (type == "2") {
            try {
                String bill = spinner_subBills.getSelectedItem().toString();
                String parameter;
                if (editText_parameter2.getText().toString() != "") {
                    parameter = editText_parameter1.getText().toString() + "/" + editText_parameter2.getText().toString();
                } else
                    parameter = editText_parameter1.getText().toString();

                String amount = editText_amountBill.getText().toString();
                float convinience = Float.parseFloat(editText_convinienceFee.getText().toString());
                String customerName = editText_customersBill.getText().toString();
                float total = Float.parseFloat(amount);
                String customersNumber = editText_customersNumberBill.getText().toString();

                return "PAY " + bill + " " + Float.toString(total) + " " + parameter.replace(" ","") + " " + customerName + " " + customersNumber;
            }catch(Exception ex){

            }
        }
        if (type == "3") {

            String cable = spinner_prepaidCable.getSelectedItem().toString();
            String amount = spinner_subPrepaidCable.getSelectedItem().toString();
            String boxNumber = editText_boxNumber.getText().toString();
            String prepaidCableCustomer = editText_prepaidCableCutomer.getText().toString();

            return cable + " " + boxNumber + " " + amount + " " + prepaidCableCustomer;
        }

        if (type == "4") {

            String companyName = spinner_sssContribution.getSelectedItem().toString();
            String member = editText_sssMember.getText().toString();
            String monthyear = editText_sssMonthYear.getText().toString();
            String sssPin = editText_sssNumber.getText().toString();
            String customerNum = editText_sss_customersNumber.getText().toString();
            String sssAmount = editText_sssAmount.getText().toString();

            return ("SSS " + companyName + "/" + member + "/" + monthyear + "/" + sssPin + "/" + customerNum + "/" + sssAmount);
        }
        if (type == "5") {

            String companyName = spinner_philHealth.getSelectedItem().toString();
            String pin = editText_philH_pin.getText().toString();
            String fname = editText_philH_fname.getText().toString();
            String mname = editText_philH_mname.getText().toString();
            String lname = editText_philH_lname.getText().toString();
            String from = editText_philH_from.getText().toString();
            String to = editText_philH_to.getText().toString();
            String amount = editText_philH_amount.getText().toString();
            String num = editText_philH_cell.getText().toString();


            return "PHILH " + companyName + "/" + pin + "/" + fname + " " + mname + " " + lname + "/" + from + "/" + to + "/" + amount + "/" + num;
        }

        return "";
    }

    public void clear(String item) {
        if (item == "1") {
            editText_eloadingCustomerNum.setText("");
        }
        if (item == "2") {

            editText_parameter1.setText("");
            editText_parameter2.setText("");
            editText_amountBill.setText("");
            editText_customersBill.setText("");
            editText_convinienceFee.setText("0");
            editText_customersNumberBill.setText("");
            editText_totalBill.setText("0");

        }
        if (item == "3") {

            editText_boxNumber.setText("");
            editText_prepaidCableCutomer.setText("");

        }

        if (item == "4") {

            editText_sssMember.setText("");
            editText_sssMonthYear.setText("");
            editText_sss_customersNumber.setText("");
            editText_sssAmount.setText("");

            editText_sssNumber.setText("");
        }
        if (item == "5") {

            editText_philH_pin.setText("");
            editText_philH_fname.setText("");
            editText_philH_mname.setText("");
            editText_philH_lname.setText("");
            editText_philH_from.setText("");
            editText_philH_to.setText("");
            editText_philH_amount.setText("");
            editText_philH_cell.setText("");
        }
    }


    public void sendSMS(String phoneNo, String msg, String item) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);

            showDialog("Sent !",item);
            clear(dummyItem.id);

        } catch (Exception ex) {
            showDialog("Error !",item);
        }finally {

        }


    }

    public boolean validateLoading() {
        boolean valid = true;

        String eloadingCustomerNum = editText_eloadingCustomerNum.getText().toString();

        if(eloadingCustomerNum.isEmpty()) {
            editText_eloadingCustomerNum.setError("Empty!");
            valid = false;
        }

        return valid;
    }

    public boolean validateBillsPayment() {
        boolean valid = true;

        String parameter1 = editText_parameter1.getText().toString();
        String amountBill = editText_amountBill.getText().toString();
        String customersBill = editText_customersBill.getText().toString();
        String customersNumberBill = editText_customersNumberBill.getText().toString();

        if(parameter1.isEmpty()){
            editText_parameter1.setError("Empty !");
            valid = false;
        }

        if(amountBill.isEmpty()){
            editText_amountBill.setError("Empty !");
            valid = false;
        }

        if(customersBill.isEmpty()){
            editText_customersBill.setError("Empty !");
            valid = false;
        }

        if(customersNumberBill.isEmpty()){
            editText_customersNumberBill.setError("Empty !");
            valid = false;
        }


        return valid;
    }

    public boolean validatePrepaidCable() {
        boolean valid = true;

        String boxNumber = editText_boxNumber.getText().toString();
        String prepaidCableCutomer = editText_prepaidCableCutomer.getText().toString();

        if(boxNumber.isEmpty()) {
            editText_boxNumber.setError("Empty!");
            valid = false;
        }

        if(prepaidCableCutomer.isEmpty()){
            editText_prepaidCableCutomer.setError("Empty!");
            valid = false;
        }

        return valid;
    }

    public void showDialog(String message,String item){
        FragmentManager fm = getFragmentManager();
        ConfirmationDialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.show(fm, message);

        clear(item);
    }

    public boolean isConnected(){
        WifiManager wifi = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            return true;
        }

        return false;
    }

        public String gatewayNumber(String description){

        Gateway gateway = Gateway.getGatewayNumber(description);


        return gateway.getNumber();
    }

    public static String gatewayNetwork(String walletPrefix){

        Prefixes prefixesGlobe = Prefixes.getPrefixesGlobe();
        String[] globe = prefixesGlobe.getGlobe().split(",");

        Prefixes prefixesSmart = Prefixes.getPrefixesSmart();
        String[] smart = prefixesSmart.getSmart().split(",");

        Prefixes prefixesSun = Prefixes.getPrefixesSun();
        String[] sun = prefixesSun.getSun().split(",");

        if(Arrays.asList(sun).contains(walletPrefix))
            return "sun";

        if(Arrays.asList(globe).contains(walletPrefix))
            return "globe";

        if(Arrays.asList(smart).contains(walletPrefix))
            return "smart";


        return "globe";
    }

    public void sendOnline(String ip_address,String source_folder,String sender, String message) {
        SendOnlineInbox sendOnlineInbox = new SendOnlineInbox();
        sendOnlineInbox.execute("http://" + ip_address + "/" + source_folder + "/getcurl_telcohttp.php?sender=" + sender + "&message=" +message);
    }

    public boolean wifiConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        }

        FragmentManager fm = getFragmentManager();
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        dialogFragment.show(fm, "Error");

        return false;
    }

    public class SendMessageHttp extends AsyncTask<Void, Void, Boolean> {

        private void postData(String message, String number) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://" + isConnectedToServer() + "/" + GetUrl.getSourceFolder() + "/getcurl_telcohttp.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("message", message));
                nameValuePairs.add(new BasicNameValuePair("sender", number));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                FragmentManager fm = getFragmentManager();
                ConfirmationDialogFragment dialogFragment = new ConfirmationDialogFragment();
                dialogFragment.show(fm, message);

            } catch (Exception e) {
                Log.e("log_tag", "Error:  " + e.toString());
                // showMessage();
//                FragmentManager fm = getFragmentManager();
//                ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
//                dialogFragment.show(fm, "Error");

            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            postData(mess, sender);
            return null;
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
}