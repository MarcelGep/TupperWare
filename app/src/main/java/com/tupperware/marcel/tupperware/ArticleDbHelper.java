package com.tupperware.marcel.tupperware;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by Marcel on 07.12.2016.
 */

public class ArticleDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = ArticleDbHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "articles.db";
    public static final String TABLE_NAME = "article";

    public static final String ID = "_id";
    public static final String ARTNR = "artnr";
    public static final String DESCRIPTION = "description";
    public static final String DIMENSIONS = "dimensions";
    public static final String CONTENT = "content";
    public static final String PRICE = "price";
    public static final String COLOR = "color";
    public static final String QUANTITY = "quantity";
    public static final String NOTE = "note";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ARTNR + " TEXT NOT NULL, " +
                    DESCRIPTION + " TEXT NOT NULL, " +
                    DIMENSIONS + " TEXT, " +
                    CONTENT + " TEXT, " +
                    PRICE + " TEXT, " +
                    COLOR + " TEXT, " +
                    QUANTITY + " TEXT NOT NULL, " +
                    NOTE + " TEXT);";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String DATABASE_PATH = "data/data/com.tupperware.marcel.tupperware/databases/" + DATABASE_NAME;

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "Verbindung zur Datenbank '" + getDatabaseName() + "' hergestellt");
    }

    // Die onCreate-Methode wird nur aufgerufen, falls die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            Log.d(LOG_TAG, "Die Datenbank '" + getDatabaseName() + "' wurde erstellt");
            Log.d(LOG_TAG, "Die Tabelle wird mit dem SQL-Befehl: " + CREATE_TABLE + " angelegt");
            db.execSQL(CREATE_TABLE);
        }
        catch (Exception e){
            Log.e(LOG_TAG, "Fehler beim Erstellen der Tabelle: " + e.getMessage());
        }
    }

    // Die onUpgrade-Methode wird aufgerufen, sobald die neue Versionsnummer höher
    // als die alte Versionsnummer ist und somit ein Upgrade notwendig wird
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "Die Tabelle mit Versionsnummer " + oldVersion + " wird entfernt.");
        db.execSQL(SQL_DROP);
        onCreate(db);
    }
}
