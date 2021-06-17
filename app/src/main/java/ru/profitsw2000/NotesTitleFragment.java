package ru.profitsw2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.profitsw2000.notes.R;

public class NotesTitleFragment extends Fragment {

    private MyNotes currentNote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view)  ;
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view   ;
        String[] notes = getResources().getStringArray(R.array.notes_title) ;

        for (int i = 0; i < notes.length; i++) {

            String note = notes[i];
            TextView textView = new TextView(getContext())  ;
            textView.setText(note);
            textView.setTextSize(30);
            layoutView.addView(textView);
            final int index = i;

/*            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = new MyNotes(getResources().getStringArray(R.array.notes_title)[index],
                            getResources().getStringArray(R.array.notes_description)[index],
                            getResources().getStringArray(R.array.notes_date)[index],
                            getResources().getStringArray(R.array.notes_text)[index]
                            );
                }
            });*/
        }
    }
}