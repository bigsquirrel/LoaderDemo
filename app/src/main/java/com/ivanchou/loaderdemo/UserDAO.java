package com.ivanchou.loaderdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ivanchou on 1/23/2015.
 */
public class UserDAO {
    private DBHelper helper = null;

    public UserDAO(Context context) {
        helper = new DBHelper(context);
        helper.getReadableDatabase();
    }

    public long insertUser(ContentValues values) {
        long id = -1;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            id = database.insert("user", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return id;
    }

    public int deleteUser(String whereClause, String[] whereArgs) {
        int count = -1;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            count = database.delete("user", whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return count;
    }

    public int updateUser(ContentValues values, String whereClause,
                             String[] whereArgs) {
        SQLiteDatabase database = null;
        int count = -1;
        try {
            database = helper.getWritableDatabase();
            count = database.update("user", values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
        }
        return count;
    }

    public Cursor queryUsers(String selection, String[] selectionArgs) {
        SQLiteDatabase database = null;
        Cursor cursor=null;
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(true, "user", null, selection,
                    selectionArgs, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
//				database.close();
            }
        }
        return cursor;
    }

}
