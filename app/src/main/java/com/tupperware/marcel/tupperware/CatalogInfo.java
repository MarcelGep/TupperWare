package com.tupperware.marcel.tupperware;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CatalogInfo extends AppCompatActivity implements View.OnClickListener {

    ArticleDataSource dataSource;

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ActionBar actionBar;

    private String artnr;
    private String description;
    private String dimensions;
    private String content;
    private String price;
    private String quantity;
    private String note;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_info);

        dataSource = new ArticleDataSource(this);

        Button btnAdd = (Button) findViewById(R.id.btnAddArticle);
        btnAdd.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapterCatalog viewPagerAdapterCatalog = new ViewPagerAdapterCatalog(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterCatalog);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            artnr = bundle.getString("artnr");
            description = bundle.getString("description");
            dimensions = bundle.getString("dimensions");
            content = bundle.getString("content");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddArticle:
                showAlertDialogView(view);
                break;
        }
    }

    public void showAlertDialogView(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogsView = inflater.inflate(R.layout.activity_new_article, null);
        final EditText etPrice = (EditText) dialogsView.findViewById(R.id.etPrice);
        final EditText etQuantity = (EditText) dialogsView.findViewById(R.id.etQuantity);
        final EditText etNote = (EditText) dialogsView.findViewById(R.id.etNote);
        final Spinner spColor = (Spinner) dialogsView.findViewById(R.id.spColor);
        String[] colorList = {"Keine Farbe", "Rot", "Blau", "Schwarz", "Weiss", "Grün", "Lila"};
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, colorList);
        spColor.setAdapter(spinneradapter);
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = spColor.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.setView(dialogsView);
        builder.setIcon(R.drawable.ic_add_circle_black_24dp);
        builder.setTitle("Artikel hinzufügen");
        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        price = etPrice.getText().toString();
                        quantity = etQuantity.getText().toString();
                        note = etNote.getText().toString();
                        /*if (TextUtils.isEmpty(price)) {
                            etPrice.setError(getString(R.string.etErrorMessage));
                            etPrice.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(quantity)) {
                            etQuantity.setError(getString(R.string.etErrorMessage));
                            etQuantity.requestFocus();
                            return;
                        }*/
                        if (!TextUtils.isEmpty(price) || !TextUtils.isEmpty(quantity)){
                            dataSource.openDB();

                            // in datanbank nach artikel suchen und ggf. bestand erhöhen oder neu anlagen

                            dataSource.addArticle(artnr, description, dimensions, content, price, color, quantity, note);
                            Toast.makeText(getApplicationContext(), "Artikel hinzugefügt", Toast.LENGTH_SHORT).show();
                            dataSource.closeDB();
                            dialog.dismiss();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Fehlerhafte eingabe!", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
}
