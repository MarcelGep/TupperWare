package com.tupperware.marcel.tupperware;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;

    DrawerLayout drawerLayoutAll;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(25);
        }

        drawerLayoutAll = (DrawerLayout) findViewById(R.id.drawerlayoutall);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayoutAll, R.string.auf, R.string.zu);
        drawerLayoutAll.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.newArticle : {
                        startActivity(new Intent(getApplicationContext(), NewCatalogArticle.class));
                        break;
                    }
                    case R.id.myTupperWare : {
                        startActivity(new Intent(getApplicationContext(), ListArticles.class));
                        break;
                    }
                    case R.id.catalog : {
                        startActivity(new Intent(getApplicationContext(), ListCatalog.class));
                        break;
                    }
                }

                drawerLayoutAll.closeDrawers();
                item.setChecked(true);
                return false;
            }
        });

        /*Button bAddArticle, bShowArticles, bShowCatalog;

        bAddArticle = (Button) findViewById(R.id.btnSaveArticle);
        bShowArticles = (Button) findViewById(R.id.btnShowArticles);
        bShowCatalog = (Button) findViewById(R.id.btnShowCatalog);

        bAddArticle.setOnClickListener(this);
        bShowArticles.setOnClickListener(this);
        bShowCatalog.setOnClickListener(this);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(new Configuration());
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.btnSaveArticle:
                startActivity(new Intent(this, NewCatalogArticle.class));
                break;

            case R.id.btnShowArticles:
                startActivity(new Intent(this, ListArticles.class));
                break;

            case R.id.btnShowCatalog:
                startActivity(new Intent(this, ListCatalog.class));
                break;
        }*/
    }
}
