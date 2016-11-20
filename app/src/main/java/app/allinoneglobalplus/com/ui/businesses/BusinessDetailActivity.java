package app.allinoneglobalplus.com.ui.businesses;

import android.os.Bundle;

import app.allinoneglobalplus.com.R;
import app.allinoneglobalplus.com.ui.base.BaseActivity;


/**
 * Simple wrapper for {@link BusinessDetailFragment}
 * This wrapper is only used in single pan mode (= on smartphones)
 * Created by Andreas Schrade on 14.12.2015.
 */
public class BusinessDetailActivity extends BaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Show the Up button in the action bar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        BusinessDetailFragment fragment = BusinessDetailFragment.newInstance(getIntent().getStringExtra(BusinessDetailFragment.ARG_ITEM_ID));
        getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();



    }


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

}
