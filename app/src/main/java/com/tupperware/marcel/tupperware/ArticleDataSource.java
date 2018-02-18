package com.tupperware.marcel.tupperware;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel on 07.12.2016.
 */

public class ArticleDataSource {

    private static final String LOG_TAG = ArticleDataSource.class.getSimpleName();

    private SQLiteDatabase db;
    private ArticleDbHelper dbHelper;

    private String[] columns = {
                        ArticleDbHelper.ID,
                        ArticleDbHelper.ARTNR,
                        ArticleDbHelper.DESCRIPTION,
                        ArticleDbHelper.DIMENSIONS,
                        ArticleDbHelper.CONTENT,
                        ArticleDbHelper.PRICE,
                        ArticleDbHelper.COLOR,
                        ArticleDbHelper.QUANTITY,
                        ArticleDbHelper.NOTE,
    };

    public ArticleDataSource(Context context) {
        dbHelper = new ArticleDbHelper(context);
    }

    public void openDB() {
        if(db == null){
            db = dbHelper.getWritableDatabase();
            Log.d(LOG_TAG, "Datenbank geöffnet: " + db.getPath());
        }
    }

    public void closeDB() {
        if (db != null && db.isOpen()){
            db.close();
            Log.d(LOG_TAG, "Datenbank geschlossen: " + db.getPath());
        }
    }

    public Articles addArticle(String artnr, String description, String dimensions, String content,
                              String price, String color, String quantity, String note) {
        openDB();
        ContentValues values = new ContentValues();
        values.put(dbHelper.ARTNR, artnr);
        values.put(dbHelper.DESCRIPTION, description);
        values.put(dbHelper.DIMENSIONS, dimensions);
        values.put(dbHelper.CONTENT, content);
        values.put(dbHelper.PRICE, price);
        values.put(dbHelper.COLOR, color);
        values.put(dbHelper.QUANTITY, quantity);
        values.put(dbHelper.NOTE, note);

        long insertId = db.insert(dbHelper.TABLE_NAME, null, values);

        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, dbHelper.ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Articles article = cursorToArticle(cursor);
        cursor.close();
        closeDB();

        return article;
    }

    private Articles cursorToArticle(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(dbHelper.ID);
        int idArtnr = cursor.getColumnIndex(dbHelper.ARTNR);
        int idDescription = cursor.getColumnIndex(dbHelper.DESCRIPTION);
        int idDimensions = cursor.getColumnIndex(dbHelper.DIMENSIONS);
        int idContent = cursor.getColumnIndex(dbHelper.CONTENT);
        int idPrice = cursor.getColumnIndex(dbHelper.PRICE);
        int idColor = cursor.getColumnIndex(dbHelper.COLOR);
        int idQuantity = cursor.getColumnIndex(dbHelper.QUANTITY);
        int idNote = cursor.getColumnIndex(dbHelper.NOTE);

        int id = cursor.getInt(idIndex);
        String artnr = cursor.getString(idArtnr);
        String description = cursor.getString(idDescription);
        String dimensions = cursor.getString(idDimensions);
        String content = cursor.getString(idContent);
        String price = cursor.getString(idPrice);
        String color = cursor.getString(idColor);
        String quantity = cursor.getString(idQuantity);
        String note = cursor.getString(idNote);

        Articles article = new Articles(id, artnr, description, dimensions, content, price, color,
                quantity, note);

        return article;
    }

    /*public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, dbHelper.TABLE_NAME, columns,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }*/

    public List<Articles> getAllArticles() {
        List<Articles> articleList = new ArrayList<>();

        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() == 0)
            return articleList;

        while (!cursor.isAfterLast()) {
            Articles article = cursorToArticle(cursor);
            articleList.add(article);
            cursor.moveToNext();
            Log.d(LOG_TAG, "ID: " + article.getId() + "\n" + article.getArticleInfo());
        }

        cursor.close();

        return articleList;
    }

    public void deleteArticle(Articles article) {
        long id = article.getId();
        db.delete(dbHelper.TABLE_NAME, dbHelper.ID + "=" + id, null);
        Log.d(LOG_TAG, "Eintrag gelöscht! \n ID: " + id + "\n" + article.toString());
    }

    public Articles updateArticle(int id, String newDimensions, String newContent, String newPrice, String newColor, String newQuantity, String newNote) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.DIMENSIONS, newDimensions);
        values.put(dbHelper.CONTENT, newContent);
        values.put(dbHelper.PRICE, newPrice);
        values.put(dbHelper.COLOR, newColor);
        values.put(dbHelper.QUANTITY, newQuantity);
        values.put(dbHelper.NOTE, newNote);

        db.update(dbHelper.TABLE_NAME, values, dbHelper.ID + "=" + id, null);

        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, dbHelper.ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        Articles article = cursorToArticle(cursor);
        cursor.close();

        return article;
    }

    /*public void deleteDatabase() {
        boolean isDeleted = dbHelper.deleteDatabase();
        if (isDeleted)
            Log.d(LOG_TAG, "Datenbank gelöscht");
        else
            Log.d(LOG_TAG, "Datenbank nicht gelöscht");
    }

    public Cursor getCatalog(){
        db = this.getWritableDatabase();
        Cursor resolve = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return resolve;
    }

    public boolean deleteDatabase(){
        try{
            db.deleteDatabase((new File(DATABASE_PATH)));
            Log.e(LOG_TAG, "Datenbanke " + DATABASE_NAME + " wurde gelöscht!");
            return true;
        }
        catch (Exception e){
            Log.e(LOG_TAG, "Fehler beim löschen der Datenbank " + DATABASE_NAME);
            return false;
        }
    }*/
}
