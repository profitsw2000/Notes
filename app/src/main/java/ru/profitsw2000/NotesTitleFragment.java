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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.profitsw2000.notes.R;

import static android.content.Context.MODE_PRIVATE;

public class NotesTitleFragment extends Fragment {

    public static NotesTitleFragment newInstance() {
        return new NotesTitleFragment() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_title, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_notes)  ;
        String[] titles = getResources().getStringArray(R.array.notes_title)  ;
        initRecyclerView(recyclerView, titles)  ;
        return view ;
    }

    private void initRecyclerView(RecyclerView recyclerView, String[] titles) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext())   ;
        recyclerView.setLayoutManager(layoutManager);

        NotesAdapter adapter = new NotesAdapter(titles) ;
        recyclerView.setAdapter(adapter);
    }

}