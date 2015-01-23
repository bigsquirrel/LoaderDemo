package com.ivanchou.loaderdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private ListView listView;
    private Button addBtn;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    DemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_list);
        addBtn = (Button) findViewById(R.id.btn_add);

        LoaderManager manager = getLoaderManager();
        manager.initLoader(0, null, myLoader);

        adapter = new DemoAdapter(list);
        listView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
            }
        });

        MyLoaderCallbacks loader = new MyLoaderCallbacks();
    }


    private LoaderCallbacks<Cursor> myLoader = new LoaderCallbacks<Cursor>() {


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Uri uri = Uri.parse("content://com.ivanchou.loaderdemo.MyContentProvider/user");

            return new CursorLoader(MainActivity.this, uri, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data == null) {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                return;
            }

            list.clear();
            while (data.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                String id = data.getString(data.getColumnIndex("id"));
                String name = data.getString(data.getColumnIndex("name"));

                map.put("id", id);
                map.put("name", name);

                list.add(map);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };


    public void add(View view) {
        final View v = LayoutInflater.from(this).inflate(R.layout.info, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Info")
                .setView(v)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etName = (EditText) v.findViewById(R.id.et_name);
                        String name = etName.getText().toString();
                        Uri uri = Uri.parse("content://com.ivanchou.loaderdemo.MyContentProvider/user");
                        ContentValues cv = new ContentValues();
                        cv.put("name", name);

                        Uri u = getContentResolver().insert(uri, cv);
                        if (u != null) {
                            MainActivity.this.getLoaderManager().restartLoader(0, null, myLoader);
                            Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    class DemoAdapter extends BaseAdapter {

        private List<Map<String, String>> list;

        public DemoAdapter(List<Map<String, String>> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            if (convertView == null) {
                v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
            } else {
                v = convertView;
            }

            TextView t1 = (TextView) v.findViewById(R.id.tv_id);
            TextView t2 = (TextView) v.findViewById(R.id.tv_name);

            t1.setText(list.get(position).get("id"));
            t2.setText(list.get(position).get("name"));
            return v;
        }
    }


}
