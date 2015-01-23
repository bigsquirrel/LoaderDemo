package com.ivanchou.loaderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";

    private UserDAO userDAO = null;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USER = 1;
    private static final int USERS = 2;

    static {
        URI_MATCHER.addURI("com.ivanchou.loaderdemo.MyContentProvider", "user", USERS);
        URI_MATCHER.addURI("com.ivanchou.loaderdemo.MyContentProvider", "user/#", USER);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = -1;
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case USER:
                long id = ContentUris.parseId(uri);
                String where_value = "id = ?";
                String[] args = { String.valueOf(id) };
                count = userDAO.deleteUser(where_value, args);
                break;
            case USERS:
                count = userDAO.deleteUser(selection, selectionArgs);
        }
        Log.e(TAG, "delete ---> count : " + count);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        int flag = URI_MATCHER.match(uri);
        String type = null;
        switch (flag) {
            case USER:
                type = "vnd.android.cursor.item/user";
                break;
            case USERS:
                type = "vnd.android.cursor.dir/users";
                break;
        }
        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        int flag = URI_MATCHER.match(uri);
        if (flag == USERS) {
            long id = userDAO.insertUser(values);
            Log.e(TAG, "insert ---> id : " + id);
            resultUri = ContentUris.withAppendedId(uri, id);
        }
        return resultUri;
    }

    @Override
    public boolean onCreate() {
        userDAO = new UserDAO(getContext());
        Log.e(TAG, "on create ------------ ");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case USER:
                long id = ContentUris.parseId(uri);
                String where_value = " id = ?";
                String[] args = { String.valueOf(id) };
                cursor = userDAO.queryUsers(where_value, args);
                break;
            case USERS:
                cursor = userDAO.queryUsers(selection, selectionArgs);
                break;
        }
        Log.e(TAG, "query ---> count : " + cursor.getCount());
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = -1;
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case USER:
                long id = ContentUris.parseId(uri);
                String where_value = " id = ?";
                String[] args = { String.valueOf(id) };
                count = userDAO.updateUser(values, where_value, args);
                break;
            case USERS:
                count = userDAO.updateUser(values, selection, selectionArgs);
                break;
        }
        Log.e(TAG, "update ---> count : " + count);
        return count;
    }
}
