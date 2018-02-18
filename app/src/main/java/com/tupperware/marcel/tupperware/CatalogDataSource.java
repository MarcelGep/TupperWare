package com.tupperware.marcel.tupperware;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel on 09.12.2016.
 */

public class CatalogDataSource {
    public static final String LOG_TAG = CatalogDataSource.class.getSimpleName();

    private SQLiteDatabase db;
    private CatalogDbHelper myCatalogDbHelper;

    private String[] columns = {
            CatalogDbHelper.ID,
            CatalogDbHelper.ARTNR,
            CatalogDbHelper.DESCRIPTION,
            CatalogDbHelper.DIMENSIONS,
            CatalogDbHelper.CONTENT,
            CatalogDbHelper.PRICE,
            CatalogDbHelper.COLOR,
            CatalogDbHelper.INFO,
    };

    public CatalogDataSource(Context context) {
        myCatalogDbHelper = new CatalogDbHelper(context);
        try {
            myCatalogDbHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDB() {
        if(db == null){
            db = myCatalogDbHelper.getReadableDatabase();
            Log.d(LOG_TAG, "Datenbank geöffnet: " + db.getPath());
        }
    }

    public void closeDB() {
        if (db != null && db.isOpen()){
            db.close();
            Log.d(LOG_TAG, "Datenbank geschlossen: " + db.getPath());
        }
    }

    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, myCatalogDbHelper.TABLE_NAME, columns,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public List<Catalog> getCatalog() {
        List<Catalog> catalogList = new ArrayList<>();
        Cursor cursor = db.query(myCatalogDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() == 0)
            return catalogList;

        while (!cursor.isAfterLast()) {
            Catalog catalog = cursorToArticle(cursor);
            catalogList.add(catalog);
            cursor.moveToNext();
            Log.d(LOG_TAG, "ID: " + catalog.getId() + "\n" + catalog.getCatalogInfo());
        }

        cursor.close();

        return catalogList;
    }

    private Catalog cursorToArticle(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(myCatalogDbHelper.ID);
        int idArtnr = cursor.getColumnIndex(myCatalogDbHelper.ARTNR);
        int idDescription = cursor.getColumnIndex(myCatalogDbHelper.DESCRIPTION);
        int idDimensions = cursor.getColumnIndex(myCatalogDbHelper.DIMENSIONS);
        int idContent = cursor.getColumnIndex(myCatalogDbHelper.CONTENT);
        int idPrice = cursor.getColumnIndex(myCatalogDbHelper.PRICE);
        int idColor = cursor.getColumnIndex(myCatalogDbHelper.COLOR);
        int idInfo = cursor.getColumnIndex(myCatalogDbHelper.INFO);

        int id = cursor.getInt(idIndex);
        String artnr = cursor.getString(idArtnr);
        String description = cursor.getString(idDescription);
        String dimensions = cursor.getString(idDimensions);
        String content = cursor.getString(idContent);
        String price = cursor.getString(idPrice);
        String color = cursor.getString(idColor);
        String info = cursor.getString(idInfo);

        Catalog catalog = new Catalog(id, artnr, description, dimensions, content, price, color,
                info);

        return catalog;
    }
}
