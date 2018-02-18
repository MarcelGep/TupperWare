package com.tupperware.marcel.tupperware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewArticle extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = NewCatalogArticle.class.getSimpleName();
    private String color;

    public ArticleDataSource datasource;
    private Button btnAdd, btnCancel;
    private EditText etPrice, etQuantity, etNote;
    public Spinner spColor;
    public String[] colorList = {"Keine Farbe", "Rot", "Blau", "Schwarz", "Weiss", "Grün", "Lila"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);

        findViewsById();
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        datasource = new ArticleDataSource(this);

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String> (
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.openDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.closeDB();
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.btnAdd:
                try{
                    addArticle();
                }
                catch(Exception e){
                    Toast.makeText(this, "Fehler beim hinzufügen des Artikels!", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Fehler beim hinzufügen des Artikels " + e.getMessage());
                }
                break;
            case R.id.btnCancel:
                finish();
                break;
        }*/
    }

    private void findViewsById() {
        //btnAdd = (Button) findViewById(R.id.btnAdd);
        //btnCancel = (Button) findViewById(R.id.btnCancel);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etNote = (EditText) findViewById(R.id.etNote);
        spColor = (Spinner) findViewById(R.id.spColor);
    }

    private void addArticle(){
        Bundle bundle = this.getIntent().getExtras();
        String artnr = "";
        String description = "";
        String dimensions = "";
        String content = "";
        String price = etPrice.getText().toString();
        String quantity = etQuantity.getText().toString();
        String note = etNote.getText().toString();

        if(bundle != null) {
            artnr = bundle.getString("artnr");
            description = bundle.getString("description");
            dimensions = bundle.getString("dimensions");
            content = bundle.getString("content");
        }

        if (TextUtils.isEmpty(price)) {
            etPrice.setError(getString(R.string.etErrorMessage));
            etPrice.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantity)) {
            etQuantity.setError(getString(R.string.etErrorMessage));
            etQuantity.requestFocus();
            return;
        }

        datasource.addArticle(artnr, description, dimensions, content, price, color, quantity, note);
        Toast.makeText(getApplicationContext(), "Artikel hinzugefügt", Toast.LENGTH_SHORT).show();
        finish();
    }
}
