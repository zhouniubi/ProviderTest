package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                Uri uri = Uri.parse("content://com.example.dictionary.provider/book");
                ContentValues values = new ContentValues();
                values.put("word","pencil");
                values.put("meaning","铅笔");
                values.put("example","I have a pencil");
                Uri newUri = getContentResolver().insert(uri,values);
                newId = newUri.getPathSegments().get(1);
            }
        });
        Button queryData = findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询
                Uri uri = Uri.parse("content://com.example.dictionary.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if(cursor != null){
                    while(cursor.moveToNext()){
                        String word = cursor.getString(cursor.getColumnIndex("word"));
                        String mean = cursor.getString(cursor.getColumnIndex("meaning"));
                        String example = cursor.getString(cursor.getColumnIndex("example"));
                        Log.d("MainActivity","word is "+word);
                        Log.d("MainActivity","mean is "+mean);
                        Log.d("MainActivity","example is "+example);
                    }
                    cursor.close();
                }
            }
        });
        Button updateData = findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //插入
                Uri uri = Uri.parse("content://com.example.dictionary.provider/book/"+newId);
                ContentValues values = new ContentValues();
                values.put("word","pencil change");
                values.put("meaning","铅笔改变");
                values.put("example","pencil has changed");
                getContentResolver().update(uri,values,null,null);
            }
        });
        Button deleteData = findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                Uri uri = Uri.parse("content://com.example.dictionary.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}
