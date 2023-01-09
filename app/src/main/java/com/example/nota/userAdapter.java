package com.example.nota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class userAdapter extends ArrayAdapter<String> {
    public userAdapter(Context context, ArrayList<String> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String note = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.relative_notes, parent, false);
        }
        // Lookup view for data population
        TextView textview = (TextView) convertView.findViewById(R.id.textview);
        // Populate the data into the template view using the data object
        textview.setText(note);
        // Return the completed view to render on screen
        return convertView;
    }
}