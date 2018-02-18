package com.tupperware.marcel.tupperware;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Marcel on 09.12.2016.
 */

public class CatalogDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = CatalogDbHelper.class.getSimpleName();

    private static String DB_PATH = "/data/data/com.tupperware.marcel.tupperware/databases/";
    private static String DB_NAME = "catalog.db";
    public static String TABLE_NAME = "catalog";

    public static final String ID = "_id";
    public static final String ARTNR = "artnr";
    public static final String DESCRIPTION = "description";
    public static final String DIMENSIONS = "dimensions";
    public static final String CONTENT = "content";
    public static final String PRICE = "price";
    public static final String COLOR = "color";
    public static final String INFO = "info";

    private final Context context;

    public CatalogDbHelper(Context context){
        super(context, DB_NAME, null, 1);
        Log.d(LOG_TAG, "Verbindung zur Datenbank '" + getDatabaseName() + "' hergestellt");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createDatabase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            Log.d(LOG_TAG, "Datenbank catalog existiert");
        }
        else{
            this.getReadableDatabase();
            try{
                copyDataBase();
                Log.d(LOG_TAG, "Datenbank kopiert");
            }
            catch (IOException e){
                throw new Error("Fehler beim kopieren der Datenbank!");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
