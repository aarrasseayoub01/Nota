package com.example.nota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

public class Note extends AppCompatActivity {

    String itemTitle;
    String itemNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        TextView title = findViewById(R.id.title);
        TextView note = findViewById(R.id.note);
        Intent intent = getIntent();
        itemTitle = intent.getStringExtra("item");
        String[] projection = { "note" };
        String selection = "title = ?";
        String[] selectionArgs = { itemTitle };
        Cursor cursor = getContentResolver().query(Notes.CONTENT_URI,projection,  selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            itemNote = cursor.getString(cursor.getColumnIndexOrThrow("note"));
        }
        title.setText(Html.fromHtml(itemTitle, Html.FROM_HTML_MODE_LEGACY));
        title.setClickable(true);
        title.setMovementMethod(LinkMovementMethod.getInstance());
        note.setText(Html.fromHtml(itemNote, Html.FROM_HTML_MODE_LEGACY));
        note.setMovementMethod(LinkMovementMethod.getInstance());
        note.setLinksClickable(true);
        }

    public void delete(View view){
        String selection = "title=?";
        String[] selectionArgs = { itemTitle };
        int count = getContentResolver().delete(Notes.CONTENT_URI, selection, selectionArgs);
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("edit", "false");
        startActivity(intent);
    }

    public void Edit(View view){
//        String selection = "title=?";
//        String[] selectionArgs = { itemTitle };
//        int count = getContentResolver().delete(Notes.CONTENT_URI, selection, selectionArgs);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.WRITING");
        intent.putExtra("title", itemTitle);
        intent.putExtra("note", itemNote);
        intent.putExtra("edit", "true");
        startActivity(intent);
    }
}