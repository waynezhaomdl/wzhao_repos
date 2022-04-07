package com.example.myapplication;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String Gluecose_TABLE_NAME = "GluecoseDB";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_FOODTYPE = "foodtype";
    public static final String CONTACTS_COLUMN_FOODNAME = "foodname";
    public static final String CONTACTS_COLUMN_INTERVAL = "timeInterval";
    public static final String CONTACTS_COLUMN_MEASEURE = "measure";
    public static final String CONTACTS_COLUMN_DATE = "datetime";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table GluecoseDB " +
                        "(id integer primary key, foodtype text,foodname text,timeInterval text, measure text, datetime text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS GluecoseDB");
        onCreate(db);
    }

    public boolean insertContact (String foodtype, String foodname, String timeInterval, String measure,String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodtype", foodtype);
        contentValues.put("foodname", foodname);
        contentValues.put("timeInterval", timeInterval);
        contentValues.put("measure", measure);
        contentValues.put("datetime", datetime);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from GluecoseDB where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Gluecose_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String foodtype, String foodname, String timeInterval, String measure,String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodtype", foodtype);
        contentValues.put("foodname", foodname);
        contentValues.put("timeInterval", timeInterval);
        contentValues.put("measure", measure);
        contentValues.put("datetime", datetime);
        db.update("GluecoseDB", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("GluecoseDB",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from GluecoseDB", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_FOODNAME)));
            res.moveToNext();
        }
        return array_list;
    }
}