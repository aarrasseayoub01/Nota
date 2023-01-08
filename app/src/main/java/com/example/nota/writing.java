package com.example.nota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class writing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
    }

    public void addNote(View view) {
        // Ajouter une note d etudiant
        ContentValues values = new ContentValues();
        values.put(Notes.TITLE,
                ((EditText) findViewById(R.id.title)).getText().toString());
        values.put(Notes.NOTE,
                ((EditText) findViewById(R.id.note)).getText().toString());

        Uri uri = getContentResolver().insert(
                Notes.CONTENT_URI, values);
        assert uri != null;
        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.LIST");
        startActivity(intent);
    }


}