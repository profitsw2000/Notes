package ru.profitsw2000.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.profitsw2000.data.MyNotes;
import ru.profitsw2000.notes.R;

public class NotesEditFragment extends Fragment {

    public final static String ARG_PARAM1 = "MyNotesData"   ;

    private MyNotes myNotes ;

    public NotesEditFragment() {

    }

    public static NotesEditFragment newInstance(MyNotes myNotes) {
        NotesEditFragment fragment = new NotesEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, myNotes);
        fragment.setArguments(args);
        return fragment;
    }

    public static NotesEditFragment newInstance() {
        NotesEditFragment fragment = new NotesEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myNotes = getArguments().getParcelable(ARG_PARAM1)  ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_edit, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}