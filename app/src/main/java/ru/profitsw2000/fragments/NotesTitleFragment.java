package ru.profitsw2000.fragments;

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

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.profitsw2000.data.CardSource;
import ru.profitsw2000.data.MyNotes;
import ru.profitsw2000.data.NotesAdapter;
import ru.profitsw2000.data.Source;
import ru.profitsw2000.notes.R;

public class NotesTitleFragment extends Fragment {

    private MyNotes currentNote ;

    public static NotesTitleFragment newInstance() {
        return new NotesTitleFragment() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_title, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_notes)  ;
        CardSource data = new Source(getResources()).init() ;
        initRecyclerView(recyclerView, data)  ;
        return view ;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext())   ;
        recyclerView.setLayoutManager(layoutManager);

        final NotesAdapter adapter = new NotesAdapter(data, this) ;
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL)    ;
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentNote = new MyNotes(getResources().getStringArray(R.array.notes_title)[position],
                        getResources().getIntArray(R.array.pictures)[position],
                        getResources().getStringArray(R.array.notes_description)[position],
                        getResources().getStringArray(R.array.notes_date)[position],
                        getResources().getStringArray(R.array.notes_text)[position]);
                showNoteText(currentNote)   ;
            }
        });
    }

    private void showNoteText(MyNotes currentNote) {
        NoteTextFragment detail = NoteTextFragment.newInstance(currentNote) ;

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()    ;
        fragmentTransaction.replace(R.id.notes_title, detail) ;
        fragmentTransaction.addToBackStack(null)    ;
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)    ;
        fragmentTransaction.commit()    ;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu.findItem(R.id.action_add) == null) inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(getContext(), "ADD", Toast.LENGTH_SHORT).show();
                return true ;
            case R.id.action_clear:
                Toast.makeText(getContext(), "CLEAR", Toast.LENGTH_SHORT).show();
                return true ;
        }
        return super.onOptionsItemSelected(item)    ;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.main_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_update:
                return true;
            case R.id.action_delete:
                return true;
        }
        return super.onContextItemSelected(item);
    }
}