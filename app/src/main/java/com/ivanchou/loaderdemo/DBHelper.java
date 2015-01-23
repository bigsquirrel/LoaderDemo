package com.ivanchou.loaderdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ivanchou on 1/23/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo.db";
    private static final int VERSION = 1;
    private static final String SQL = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, create_at INTEGER, jointed INTEGER, author_id INTEGER, start_at INTEGER, \n" +
            "place_at TEXT, name TEXT, text TEXT, tags INTEGER, thumbnail_pic TEXT, original_pic TEXT)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DBHelper", "-------------------db on create-----------------------");
//        String sql="create table user (id integer primary key autoincrement,name varchar(64),address varchar(64))";
        db.execSQL(SQL);
        ContentValues cv1=new ContentValues();
        cv1.put("name", "jack");
        db.insert("user", null, cv1);
        cv1.clear();
        cv1.put("name", "Emma");
        db.insert("user", null, cv1);
        cv1.clear();
        cv1.put("name", "Dick");
        db.insert("user", null, cv1);
        cv1.clear();
        cv1.put("name", "Tim");
        db.insert("user", null, cv1);
        cv1.clear();
        cv1.put("name", "jimmy");
        db.insert("user", null, cv1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
