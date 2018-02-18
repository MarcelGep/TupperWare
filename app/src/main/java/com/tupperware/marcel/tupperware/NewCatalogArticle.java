package com.tupperware.marcel.tupperware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCatalogArticle extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = NewCatalogArticle.class.getSimpleName();

    public ArticleDataSource datasource;

    private Button btnSave;
    private EditText etArtnr, etDescription, etDimensions, etContent, etPrice, etColor, etQuantity, etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_catalog_article);

        findViewsById();
        etArtnr.requestFocus();
        btnSave.setOnClickListener(this);
        datasource = new ArticleDataSource(this);
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
        switch (view.getId()) {
            case R.id.btnSaveArticle:
                try{
                    addArticle();
                }
                catch(Exception e){
                    Toast.makeText(this, "Fehler beim hinzufügen des Artikels!", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Fehler beim hinzufügen des Artikels " + e.getMessage());
                }
                break;
        }
    }

    private void findViewsById() {
        btnSave = (Button) findViewById(R.id.btnSaveArticle);

        etArtnr = (EditText) findViewById(R.id.etArtNr);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etDimensions = (EditText) findViewById(R.id.etDimensions);
        etContent = (EditText) findViewById(R.id.etContent);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etColor = (EditText) findViewById(R.id.etColor);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etNote = (EditText) findViewById(R.id.etNote);
    }

    private void addArticle(){
        String artnr = etArtnr.getText().toString();
        String description = etDescription.getText().toString();
        String dimensions = etDimensions.getText().toString();
        String content = etContent.getText().toString();
        String price = etPrice.getText().toString();
        String color = etColor.getText().toString();
        String quantity = etQuantity.getText().toString();
        String note = etNote.getText().toString();

        if (TextUtils.isEmpty(artnr)) {
            etArtnr.setError(getString(R.string.etErrorMessage));
            etArtnr.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            etDescription.setError(getString(R.string.etErrorMessage));
            etDescription.requestFocus();
            return;
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

        etArtnr.setText("");
        etDescription.setText("");
        etDimensions.setText("");
        etContent.setText("");
        etPrice.setText("");
        etColor.setText("");
        etQuantity.setText("");
        etNote.setText("");

        etArtnr.requestFocus();

        datasource.addArticle(artnr, description, dimensions, content, price, color, quantity, note);
        Toast.makeText(getApplicationContext(), "Artikel hinzugefügt", Toast.LENGTH_SHORT).show();

        /*InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }*/
    }
}
