package app.allinoneglobalplus.com.ui.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import app.allinoneglobalplus.com.R;
import app.allinoneglobalplus.com.ui.businesses.BusinessListActivity;

import static app.allinoneglobalplus.com.util.LogUtil.logD;
import static app.allinoneglobalplus.com.util.LogUtil.makeLogTag;


/**
 * The base class for all Activity classes.
 * This class creates and provides the navigation drawer and toolbar.
 * The navigation logic is handled in {@link BaseActivity#goToNavDrawerItem(int)}
 *
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = makeLogTag(BaseActivity.class);

    protected static final int NAV_DRAWER_ITEM_INVALID = -1;

    private DrawerLayout drawerLayout;
    private Toolbar actionBarToolbar;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    /**
     * Sets up the navigation drawer.
     */
    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerSelectListener(navigationView);
            setSelectedItem(navigationView);
        }

        logD(TAG, "navigation drawer setup finished");
    }

    /**
     * Updated the checked item in the navigation drawer
     * @param navigationView the navigation view
     */
    private void setSelectedItem(NavigationView navigationView) {
        // Which navigation item should be selected?
        int selectedItem = getSelfNavDrawerItem(); // subclass has to override this method
        navigationView.setCheckedItem(selectedItem);
    }

    /**
     * Creates the item click listener.
     * @param navigationView the navigation view
     */
    private void setupDrawerSelectListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        onNavigationItemClicked(menuItem.getItemId());
                        return true;
                    }
                });
    }

    /**
     * Handles the navigation item click.
     * @param itemId the clicked item
     */
    private void onNavigationItemClicked(final int itemId) {
        if(itemId == getSelfNavDrawerItem()) {
            // Already selected
            closeDrawer();
            return;
        }

        goToNavDrawerItem(itemId);
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     * @param item the selected navigation item
     */
    private void goToNavDrawerItem(int item) {
        switch (item) {
            case R.id.nav_quotes:
                startActivity(new Intent(this, BusinessListActivity.class));
                finish();
                break;
            case R.id.nav_visit:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.allinoneglobalplus.com"));
                startActivity(browserIntent);
                break;
            case R.id.nav_update:

                Uri packageUri = Uri.parse("package:app.allinoneglobalplus.com");
                Intent uninstallIntent =
                        new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                startActivity(uninstallIntent);
//                Retailers retailers = Retailers.getRetailersSession();
//                retailers.updateSessionActive("2");
//                startActivity(new Intent(this, LoginActivity.class));
//                finish();

                break;
//            case R.id.nav_logout:
//
//                Retailers retailers1 = Retailers.getRetailersSession();
//                retailers1.updateSessionActive("0");
//                startActivity(new Intent(this, LoginActivity.class));
//                finish();
//
//                break;
        }
    }

    /**
     * Provides the action bar instance.
     * @return the action bar.
     */
    protected ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return getSupportActionBar();
    }


    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * have to override this method.
     */
    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    protected void openDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if(drawerLayout == null)
            return;

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showMessage() {

        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context,"Fetching Data!", Toast.LENGTH_SHORT);

        toast.show();
    }



    public abstract boolean providesActivityToolbar();


}
