package ru.profitsw2000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.profitsw2000.notes.R;

import static android.content.Context.MODE_PRIVATE;

public class NotesTitleFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote"  ;
    private MyNotes currentNote;
    private boolean isLandscape ;
    private static final String NameSharedPreference = "SP";
    private static final String ID = "index";
    private int currentIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList((LinearLayout) view)  ;
    }

    private void initList(LinearLayout view) {
        String[] notes = getResources().getStringArray(R.array.notes_title) ;

        for (int i = 0; i < notes.length; i++) {

            String note = notes[i];
            TextView textView = new TextView(getContext())  ;
            textView.setText(note);
            textView.setTextSize(20);
            view.addView(textView);
            final int index = i;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = new MyNotes(getResources().getStringArray(R.array.notes_title)[index],
                            getResources().getStringArray(R.array.notes_description)[index],
                            getResources().getStringArray(R.array.notes_date)[index],
                            getResources().getStringArray(R.array.notes_text)[index]
                            );
                    showNoteText(currentNote)   ;
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        saveId();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        restoreId();

        currentNote = new MyNotes(getResources().getStringArray(R.array.notes_title)[currentIndex],
                getResources().getStringArray(R.array.notes_description)[currentIndex],
                getResources().getStringArray(R.array.notes_date)[currentIndex],
                getResources().getStringArray(R.array.notes_text)[currentIndex]
        );

/*        if(savedInstanceState != null){
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE)   ;
        }
        else {
            currentNote = new MyNotes(getResources().getStringArray(R.array.notes_title)[0],
                    getResources().getStringArray(R.array.notes_description)[0],
                    getResources().getStringArray(R.array.notes_date)[0],
                    getResources().getStringArray(R.array.notes_text)[0]
            );
        }*/

        if (isLandscape) {
            showLandNoteText(currentNote)   ;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE  ;
    }

    private void showNoteText(MyNotes currentNote) {
        if (isLandscape) {
            showLandNoteText(currentNote);
        }
        else {
            showPortraitNoteText(currentNote);
        }
    }

    private void showLandNoteText(MyNotes currentNote) {
        NoteTextFragment detail = NoteTextFragment.newInstance(currentNote) ;

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()    ;
        fragmentTransaction.replace(R.id.text_fragment, detail) ;
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)    ;
        fragmentTransaction.commit()    ;
    }

    private void showPortraitNoteText(MyNotes currentNote) {
        Intent intent = new Intent()    ;
        intent.setClass(getActivity(), NotesTextActivity.class) ;
        intent.putExtra(NoteTextFragment.ARG_NOTE, currentNote) ;
        startActivity(intent);
    }

    private void saveId(){
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ID, currentIndex);
        editor.apply();
    }

    private void restoreId(){
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        currentIndex = sharedPref.getInt(ID, 0);
    }
}