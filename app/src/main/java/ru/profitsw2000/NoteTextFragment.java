package ru.profitsw2000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.profitsw2000.notes.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteTextFragment extends Fragment {

    private static final String ARG_NOTE = "note";
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

        TextView description = view.findViewById(R.id.description)  ;
        TextView date = view.findViewById(R.id.date)  ;
        TextView text = view.findViewById(R.id.text)  ;

        description.setText(currentNote.getDescription());
        date.setText(currentNote.getData());
        text.setText(currentNote.getText());

        return view;
    }
}