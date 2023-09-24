package anish.tutorial.toc.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "TOC";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME="VAR_TABLE";
    public static final String ID = "ID";
    public static final String VARS = "VARS";
    SQLiteDatabase db = getWritableDatabase();

    public SQLiteAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String iQuery = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + VARS + " VARCHAR(999) )";
            db.execSQL(iQuery);
        }catch (Exception e){
            Log.e("Create Table error ", e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String iquery = " DROP TABLE IF EXISTS " + TABLE_NAME + " ; ";
        db.execSQL(iquery);
    }

    public void insertVar(String vars){
        @SuppressLint("Recycle") Cursor checkVar = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + VARS + " = ? ", new String[]{vars});
        if (checkVar.getCount()>0){
            if (checkVar.moveToNext()){
                do{
                    Log.e("VAR exists ", checkVar.getString(checkVar.getColumnIndex(VARS)));
                }while(checkVar.moveToNext());
            }
        }else {
            ContentValues cv = new ContentValues();
            cv.put(VARS, vars);
            db.insert(TABLE_NAME,null,cv);
            Log.e("Item Inserted", vars);
        }

    }

    public List<String> getVars(){
        List<String> vars = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cs = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " order by " + VARS + " ASC ", null);
        if (cs.moveToNext()){
            do {
                vars.add(cs.getString(cs.getColumnIndex(VARS)));
            }while (cs.moveToNext());
        }
        return vars;
    }
}
