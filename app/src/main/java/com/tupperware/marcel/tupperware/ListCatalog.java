package com.tupperware.marcel.tupperware;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListCatalog extends AppCompatActivity{

    public static final String LOG_TAG = ListArticles.class.getSimpleName();

    public ListView catalogListView;
    public CatalogDataSource datasource;

    List<Catalog> catalogList = new ArrayList<>();

    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_catalog);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        datasource = new CatalogDataSource(this);
        catalogListView = (ListView) findViewById(R.id.lvCatalog);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings)
            return true;

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.openDB();
        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showCatalog();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void showCatalog() {
        try{
            catalogList.clear();
            catalogList = datasource.getCatalog();
        }
        catch (Exception e){
            Toast.makeText(this, "Error showCatalog!", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<Catalog> catalogArrayAdapter = new CatalogListAdapter(this, R.layout.catalog_list_layout, R.id.tvArtNr, catalogList);

        catalogListView.setAdapter(catalogArrayAdapter);

        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Catalog catalog = (Catalog) adapterView.getItemAtPosition(position);
                Intent showCatalogInfo = new Intent(getApplicationContext(), CatalogInfo.class);
                showCatalogInfo.putExtra("artnr", catalog.getArtnr());
                showCatalogInfo.putExtra("description", catalog.getDescription());
                showCatalogInfo.putExtra("dimensions", catalog.getDimensions());
                showCatalogInfo.putExtra("content", catalog.getContent());
                showCatalogInfo.putExtra("price", catalog.getPrice());
                showCatalogInfo.putExtra("color", catalog.getColor());
                showCatalogInfo.putExtra("info", catalog.getInfo());
                showCatalogInfo.putExtra("picid", catalog.getPicId());

                startActivity(showCatalogInfo);
            }
        });
    }
}
