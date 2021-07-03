package ru.profitsw2000.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ru.profitsw2000.data.MyNotes;
import ru.profitsw2000.notes.R;

public class NoteTextFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private MyNotes currentNote;

    public static NoteTextFragment newInstance(MyNotes currentNote) {
        NoteTextFragment fragment = new NoteTextFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, currentNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_text, container, false);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy")  ;
        TextView description = view.findViewById(R.id.description)  ;
        TextView date = view.findViewById(R.id.date)  ;
        TextView text = view.findViewById(R.id.text)  ;

        if (currentNote != null) {
            description.setText(currentNote.getDescription());
            date.setText(dateFormat.format(currentNote.getDate()));
            text.setText(currentNote.getText());
        }

        return view;
    }
}