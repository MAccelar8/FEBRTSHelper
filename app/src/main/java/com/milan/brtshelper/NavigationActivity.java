package com.milan.brtshelper;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.milan.brtshelper.R.string.drawer_close;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private android.support.v7.widget.Toolbar mToolbar;
    private DrawerLayout mDrawer;

    private String uid;
    
    android.widget.SearchView searchviewmain;
    android.widget.SearchView searchviewdestmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        FirebaseApp.initializeApp(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getPhoneNumber().toString().trim();

        } else {
            Intent i =new Intent(NavigationActivity.this,LoginActivity.class);
            startActivity(i);
        }
        Toast.makeText(NavigationActivity.this,uid,Toast.LENGTH_LONG).show();
        setupToolbar();
        setupNavDrawermenu(uid);



        EditText editt = findViewById(R.id.editText);
        editt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NavigationActivity.this, v ,"src_shared_searchbar_main");
                Intent i = new Intent(NavigationActivity.this, SearchSouceFromMain.class);
                startActivityForResult(i,1);
                return false;
            }
        });

    }

    private void setupNavDrawermenu(String uid) {
        NavigationView navview = (NavigationView) findViewById(R.id.navviewmain);
        View headerview = navview.getHeaderView(0);
        TextView navheaderid = (TextView) headerview.findViewById(R.id.phonenumberuser);
        navheaderid.setText(uid);
        navview.setNavigationItemSelectedListener(this);
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("BRTS Helper");
        mDrawer = (DrawerLayout) findViewById(R.id.drawerlayout);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                NavigationActivity.this,
                mDrawer,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }



    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        Log.i("hghgg", "Hardik");
        if (requestcode == 2) {
            if (resultcode == RESULT_OK) {
                Log.i("hghgg", "Hardik222222222222222");
                searchviewdestmain = (android.widget.SearchView) findViewById(R.id.searchbar_main_dest);
                String temp = (String) data.getExtras().getString("passsrc");
                Log.i("112222222", temp);


                searchviewdestmain.setIconifiedByDefault(true);
                searchviewdestmain.setFocusable(true);
                searchviewdestmain.setIconified(false);
//                searchviewmain.requestFocus();
                searchviewdestmain.requestFocusFromTouch();
                searchviewdestmain.setQuery(temp, false);
                searchviewdestmain.clearFocus();

            }
        }
        if (requestcode == 1) {
            if (resultcode == RESULT_OK) {
//                Log.i("hghgg", "Hardik1111111111111111");
//                searchviewmain = (android.widget.SearchView) findViewById(R.id.searchbar_main_src);
//
//
                String temp = (String) data.getExtras().getString("passsrc");
//                Log.i("11hghgg", temp);
//                searchviewmain.setIconifiedByDefault(true);
//                searchviewmain.setFocusable(true);
//                searchviewmain.setIconified(false);

                EditText editt = findViewById(R.id.editText);
                editt.setText(temp,EditText.BufferType.EDITABLE);
                editt.clearFocus();

////                searchviewmain.requestFocus();
//                searchviewmain.requestFocusFromTouch();
//                searchviewmain.setQuery(temp, false);
//                searchviewmain.clearFocus();

            }

        }

    }




    public void onmainButtonclicked (View view){

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void onDestinationMainClicked(View view){

    }


    public void onSearchbar1click(View view){

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view ,"src_shared_searchbar_main");
        Intent i = new Intent(this, SearchSouceFromMain.class);
        startActivityForResult(i,1);

    }
    public void onSearchbar2click(View view){

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view ,"src_shared_searchbar_main");
        Intent i = new Intent(this, SearchSouceFromMain.class);
        startActivityForResult(i,2);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        String itemname = (String) menuItem.getTitle();

        closeDrawer();
        Intent i;

        switch (menuItem.getItemId()){
            case   R.id.navprofile:
                i = new Intent(NavigationActivity.this, ProfilePageActivity.class);
                startActivity(i);
                break;
            case R.id.navmap:
                i = new Intent(NavigationActivity.this, NavigationMapActivity.class);
                startActivity(i);
                break;
            case R.id.navbuslist:
                i = new Intent(NavigationActivity.this, BusListActivity.class);
                startActivity(i);
                break;

            case R.id.navstationlist:
                i = new Intent(NavigationActivity.this, StationListActivity.class);
                startActivity(i);
                break;

            case R.id.navcontactus:
                i = new Intent(NavigationActivity.this, ContactUsActivity.class);
                startActivity(i);
                break;
        }



        return false;
    }

    private void closeDrawer() {
        mDrawer.closeDrawer(GravityCompat.START);
    }

    public void onBackPressed(){
        if (mDrawer.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        else {
            super.onBackPressed();
        }
    }
}
