package app.allinoneglobalplus.com.ui.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.allinoneglobalplus.com.R;
import app.allinoneglobalplus.com.database.handler.Retailers;
import app.allinoneglobalplus.com.ui.login.LoginActivity;

/**
 * Just dummy content. Nothing special.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class BusinessContent {

    /**
     * An array of sample items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample items. Key: sample ID; Value: Item.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>(6);

    static {

        String type = "";

        try {
            Retailers retailers0 = Retailers.getRetailerByWalletIdPassword(LoginActivity.getWallet_id(), LoginActivity.getPassword());
            type = retailers0.getType();
        }catch(Exception ex) {

            Retailers retailers = Retailers.getRetailersSession();
            type = retailers.getType();
        }

        String s = "";

        if(type.contains("0"))
            s = "1";
        else if(type.contains("1"))
            s = "123";
        else if(type.contains("2"))
            s = "123";

        if(s.contains("1"))
            addItem(new DummyItem("1", R.drawable.loading, "E-Loading", "All Net", "Focusing is about saying ",R.layout.include_form_eloading));
        if(s.contains("2"))
            addItem(new DummyItem("2", R.drawable.billspayment, "Bills Payment", "All Bill","A quitter never wins and a winner never quits.",R.layout.include_form_paybills));
        if(s.contains("3"))
            addItem(new DummyItem("3", R.drawable.cable, "Prepaid Cable Loading", "Prepaid Cable", "Action is the foundational key to all success.",R.layout.include_form_prepaidcable));
        if(s.contains("4"))
            addItem(new DummyItem("4", R.drawable.sss, "SSS Contribution Payment", "SSS Bill","Action is the foundational key to all success.",R.layout.include_form_sssbill));
        if(s.contains("5"))
            addItem(new DummyItem("5", R.drawable.philhealth, "PhilHealth Premium Payment", "PhilHealth Bill","Our only limitations are those we set up in our own minds.",R.layout.include_form_philhealthbill));

    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class DummyItem {
        public final String id;
        public final int photoId;
        public final String title;
        public final String author;
        public final String content;
        public final int formContent;

        public DummyItem(String id, int photoId, String title, String author, String content, int formContent) {
            this.id = id;
            this.photoId = photoId;
            this.title = title;
            this.author = author;
            this.content = content;
            this.formContent = formContent;
        }
    }
}
