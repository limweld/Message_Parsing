package app.allinoneglobalplus.com.database.handler;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by acer on 3/7/2016.
 */
public class DataHandler {
    Context context;
    public static SQLiteDatabase db;

    private final String DBNAME = "eloadtest";
    private final int DBVER = 9;

    //TELCO TABLE
    private final String TBLNAME =   "telco";
    private final String TELCONAME   = "telconame";


    //PRODUCT TABLE
    private final String TBLNAME2    = "telcopromo";
    private final String TELCOPRODUCTS_ID = "_id";
    private final String TELCOPRODUCTSNAME = "telconame";
    private final String USERKEYWORD = "userkeyword";


    //GATEWAY TABLE
    private final String TBLNAME3 = "gateway";
    private final String GATEWAYDESCRIPTION = "description";
    private final String GATEWAYNUMBER = "number";


    //BILLERS TABLE
    private final String TBLNAME4 = "billercategory";
    private final String BILLERS_ID = "_id";
    private final String BILLERSCATEGORY = "category";
    private final String TBLNAME5 = "billers";
    private final String BILLERSPRODUCTS_ID = "_id";
    private final String BILLERSPRODUCTSCATEGORY = "category_code";
    private final String BILLERTAG = "billers_tag";
    private final String BILLERSTAG_FIRSTFIELD = "firstfield";
    private final String BILLERSTAG_SECONDFIELD = "secondfield";
    private final String SERVICECHARGE = "service_charge";

    //PREPAIDCABLE TABLE
    private final String TBLNAME6 = "prepaidcable";
    private final String PREPAIDCABLE_ID = "_id";
    private final String PREPAIDCABLE_TYPE = "code";

    //PREPAIDCABLECARDSAMOUNT TABLE
    private final String TBLNAME7 = "prepaidcableamounts";
    private final String PREPAIDCABLEAMOUNTS_ID = "_id";
    private final String PREPAIDCABLEAMOUNTS_TYPE = "type";
    private final String PREPAIDCABLEAMOUNTS_AMOUNT = "amount";

    //SSSCONTRIBUTION TABLE
    private final String TBLNAME8 = "ssscontribution";
    private final String SSSCONTRIBUTIONS_ID = "_id";
    private final String SSSCONTRIBUTIONS_COMPANY_NAME = "company_name";

    //PHILHEALTHPREMIUM TABLE
    private final String TBLNAME9 = "philhealthpremium";
    private final String PHILHEALTHPREMIUM_ID = "_id";
    private final String PHILHEALTHPREMIUM_ID_COMPANY_NAME = "company_name";

    //RETAILER TABLE
    private final String TBLNAME10 = "retailers";
    private final String RETAILERS_WALLET_ID = "wallet_id";
    private final String RETAILERS_PASSWORD = "password";
    private final String RETAILERS_SESSION = "session";
    private final String RETAILERS_TYPE = "type";

    //PREFIXES TABLE
    private final String TBLNAME11 = "prefixes";
    private final String PREFIX_ID = "_id";
    private final String PREFIX_SMART = "smart";
    private final String PREFIX_GLOBE = "globe";
    private final String PREFIX_SUN = "sun";

    public DataHandler(){

    }

    public DataHandler(Context context)
    {
        this.context = context;
        CustomHelper helper = new CustomHelper(context);
        this.db = helper.getWritableDatabase();

    }

    private class CustomHelper extends SQLiteOpenHelper {

        public CustomHelper(Context context) {
            super(context, DBNAME, null, DBVER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                String prepaidCable = "create table " + TBLNAME6 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + PREPAIDCABLE_ID + " INTEGER PRIMARY KEY,"
                        + PREPAIDCABLE_TYPE + " TEXT "
                        + ");";
                db.execSQL(prepaidCable);

                String billers = "create table " + TBLNAME4 +
                        " ("
                        + BILLERS_ID + " INTEGER PRIMARY KEY,"
                        + BILLERSCATEGORY + " TEXT "
                        + ");";
                db.execSQL(billers);

                String sql = "create table " + TBLNAME +
                        " ("
                        + TELCONAME + " TEXT "
                        + ");";

                db.execSQL(sql);

                String telcoproducts = "create table " + TBLNAME2 +
                        " ("
                        + TELCOPRODUCTS_ID + " INTEGER PRIMARY KEY,"
                        + TELCOPRODUCTSNAME + " TEXT,"
                        + USERKEYWORD + " TEXT "
                        + ");";
                db.execSQL(telcoproducts);

                String tel = "create table " + TBLNAME3 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + GATEWAYDESCRIPTION + " TEXT PRIMARY KEY,"
                        + GATEWAYNUMBER + " TEXT "
                        + ");";
                db.execSQL(tel);


                String billerproducts = "create table " + TBLNAME5 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + BILLERSPRODUCTS_ID + " INTEGER PRIMARY KEY,"
                        + BILLERSPRODUCTSCATEGORY + " TEXT,"
                        + BILLERTAG + " TEXT,"
                        + BILLERSTAG_FIRSTFIELD + " TEXT,"
                        + BILLERSTAG_SECONDFIELD + " TEXT,"
                        + SERVICECHARGE + " TEXT "
                        + ");";
                db.execSQL(billerproducts);


                String prepaidCableAmounts = "create table " + TBLNAME7 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + PREPAIDCABLEAMOUNTS_ID + " INTEGER PRIMARY KEY,"
                        + PREPAIDCABLEAMOUNTS_TYPE + " TEXT,"
                        + PREPAIDCABLEAMOUNTS_AMOUNT + " TEXT "
                        + ");";
                db.execSQL(prepaidCableAmounts);

                String ssscontribtuions = "create table " + TBLNAME8 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + SSSCONTRIBUTIONS_ID + " INTEGER PRIMARY KEY,"
                        + SSSCONTRIBUTIONS_COMPANY_NAME + " TEXT "
                        + ");";
                db.execSQL(ssscontribtuions);

                String philhealthpremium = "create table " + TBLNAME9 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + PHILHEALTHPREMIUM_ID + " INTEGER PRIMARY KEY,"
                        + PHILHEALTHPREMIUM_ID_COMPANY_NAME + " TEXT "
                        + ");";
                db.execSQL(philhealthpremium);

                String retailer = "create table " + TBLNAME10 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + RETAILERS_WALLET_ID + " TEXT PRIMARY KEY,"
                        + RETAILERS_PASSWORD + " TEXT,"
                        + RETAILERS_TYPE + " TEXT,"
                        + RETAILERS_SESSION + " TEXT "
                        + ");";
                db.execSQL(retailer);

                String prefix = "create table " + TBLNAME11 +
                        " (" /*+ PID + " integer primary key autoincrement not null,"*/
                        + PREFIX_ID + " INTEGER PRIMARY KEY,"
                        + PREFIX_GLOBE + " TEXT,"
                        + PREFIX_SMART + " TEXT,"
                        + PREFIX_SUN + " TEXT "
                        + ");";
                db.execSQL(prefix);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME2);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME3);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME4);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME5);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME6);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME7);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME8);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME9);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME10);

                db.execSQL("DROP TABLE IF EXISTS " + TBLNAME11);

                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
