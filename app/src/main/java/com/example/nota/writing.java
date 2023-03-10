package com.example.nota;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class writing extends AppCompatActivity {
    String htmlNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(Color.parseColor("#089c8d"));
        window.setStatusBarColor(Color.parseColor("#089c8d"));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#089c8d")));

        }
        Button button = findViewById(R.id.button2);
        button.setBackgroundColor(Color.parseColor("#0AD7C3"));
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
            note.setText((Html.fromHtml(inputNote, Html.FROM_HTML_MODE_LEGACY)));

            btn.setText("Update");
        }

    }

    public void addNote(View view) {
        // Ajouter une note d etudiant

        String inputEdit = getIntent().getStringExtra("edit");

        if (Objects.equals(inputEdit, "true")) {
            ContentValues values = new ContentValues();
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            values.put(Notes.DATE, "Modified at " +String.format("%d-%d-%d %d:%d:%d", year, month+1, day, hour, minute, second));
            values.put(Notes.TITLE,
                    ((EditText) findViewById(R.id.title)).getText().toString());
            values.put(Notes.NOTE,
                    Html.toHtml(((EditText) findViewById(R.id.note)).getText(), Html.FROM_HTML_MODE_LEGACY));
            ContentResolver resolver = getContentResolver();
            Uri uri = Uri.parse("content://com.example.nota.Notes/Notes");
            String selection = "title = ?";
            String[] selectionArgs = {inputTitle};
            int count = resolver.update(uri, values, selection, selectionArgs);
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        } else {
            ContentValues values = new ContentValues();
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            values.put(Notes.DATE, "Created at " +String.format("%d-%d-%d %d:%d:%d", year, month+1, day, hour, minute, second));

            values.put(Notes.TITLE,
                    ((EditText) findViewById(R.id.title)).getText().toString());
            values.put(Notes.NOTE,
                    Html.toHtml(((EditText) findViewById(R.id.note)).getText(), Html.FROM_HTML_MODE_LEGACY));

            Uri uri = getContentResolver().insert(
                    Notes.CONTENT_URI, values);
            assert uri != null;
            Toast.makeText(getBaseContext(),
                    uri.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        }
    }

public void addLink(View view) {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Contact Form");

    LinearLayout layout = new LinearLayout(this);
    layout.setOrientation(LinearLayout.VERTICAL);

    final EditText inputName = new EditText(this);
    inputName.setInputType(InputType.TYPE_CLASS_TEXT);
    inputName.setHint("Enter the Url");
    layout.addView(inputName);

    final EditText inputEmail = new EditText(this);
    inputEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    inputEmail.setHint("Enter the expression");
    layout.addView(inputEmail);

    builder.setView(layout);

    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String url = inputName.getText().toString();
            String expression = inputEmail.getText().toString();
            EditText note = findViewById(R.id.note);
            String text = Html.toHtml(((EditText) findViewById(R.id.note)).getText(), Html.FROM_HTML_MODE_LEGACY)+ " <a href='" + url + "'>" + expression + "</a>";
            note.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
            note.setMovementMethod(LinkMovementMethod.getInstance());
            note.setLinksClickable(true);
            htmlNote = text;

        }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });

    builder.show();
}

}