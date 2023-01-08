package com.example.nota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class writing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

    }
    String inputTitle;
    public void onStart(){
        super.onStart();
        EditText title = findViewById(R.id.title);
        EditText note = findViewById(R.id.note);
        Button btn = findViewById(R.id.button);
        Intent intent = new Intent();
        String inputEdit = getIntent().getStringExtra("edit");
        if (Objects.equals(inputEdit, "true")) {
            inputTitle = getIntent().getStringExtra("title");
            String inputNote = getIntent().getStringExtra("note");
            title.setText(inputTitle);
            note.setText(inputNote);
            btn.setText("Update");
        }

    }

    public void addNote(View view) {
        // Ajouter une note d etudiant

        String inputEdit = getIntent().getStringExtra("edit");

        if (Objects.equals(inputEdit, "true")) {
            ContentValues values = new ContentValues();
            values.put(Notes.TITLE,
                    ((EditText) findViewById(R.id.title)).getText().toString());
            values.put(Notes.NOTE,
                    ((EditText) findViewById(R.id.note)).getText().toString());
            ContentResolver resolver = getContentResolver();
            Uri uri = Uri.parse("content://com.example.nota.Notes/Notes");
            String selection = "title = ?";
            String[] selectionArgs = {inputTitle};
            int count = resolver.update(uri, values, selection, selectionArgs);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.LIST");
            startActivity(intent);
        } else {
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


}