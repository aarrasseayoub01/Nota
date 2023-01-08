package com.example.nota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String URL = "content://com.example.nota.Notes";
        Uri Notes1 = Uri.parse(URL);
        Cursor c;
        c = getContentResolver().query(Notes1, null, null, null, "note");
        ArrayList<String> data = new ArrayList<>();;
        if (c.moveToFirst()) {
            do data.add(c.getString(c.getColumnIndex(Notes.TITLE))); while (c.moveToNext());
        }
        Bundle args = new Bundle();
        args.putStringArrayList("list",data);

// set the arguments on the fragment
        NoteList fragment = new NoteList();
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String URL = "content://com.example.nota.Notes";
        Uri Notes1 = Uri.parse(URL);
        Cursor c;
        c = getContentResolver().query(Notes1, null, null, null, "note");
        ArrayList<String> data = new ArrayList<>();;
        if (c.moveToFirst()) {
            do data.add(c.getString(c.getColumnIndex(Notes.TITLE))); while (c.moveToNext());
        }
        Bundle args = new Bundle();
        args.putStringArrayList("list",data);

// set the arguments on the fragment
        NoteList fragment = new NoteList();
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    public void Writing(View view){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.WRITING");
        startActivity(intent);


    }

}