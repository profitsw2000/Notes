package ru.profitsw2000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        CardSource data = new Source(getResources()).init() ;
        initRecyclerView(recyclerView, data)  ;
        return view ;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext())   ;
        recyclerView.setLayoutManager(layoutManager);

        final NotesAdapter adapter = new NotesAdapter(data) ;
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL)    ;
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(getContext(), String.format("%d", position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}