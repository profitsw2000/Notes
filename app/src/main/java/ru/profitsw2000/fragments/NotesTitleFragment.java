package ru.profitsw2000.fragments;

import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.profitsw2000.MainActivity;
import ru.profitsw2000.data.CardSource;
import ru.profitsw2000.data.MyNotes;
import ru.profitsw2000.data.NotesAdapter;
import ru.profitsw2000.data.Source;
import ru.profitsw2000.nav.Navigation;
import ru.profitsw2000.nav.Observer;
import ru.profitsw2000.nav.Publisher;
import ru.profitsw2000.notes.R;

public class NotesTitleFragment extends Fragment {

    private MyNotes currentNote ;
    private RecyclerView recyclerView   ;
    private CardSource data ;
    private NotesAdapter adapter    ;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;


    public static NotesTitleFragment newInstance() {
        return new NotesTitleFragment() ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            data = new Source(getResources()).init() ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_title, container, false);

        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_notes)  ;
        initRecyclerView(recyclerView, data)  ;
        return view ;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext())   ;
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(data, this) ;
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL)    ;
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

        if (moveToLastPosition){
            recyclerView.smoothScrollToPosition(data.size() - 1);
            moveToLastPosition = false;
        }

        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) throws ParseException {
                showNoteText(data.getMyNotes(position))   ;
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
                navigation.addFragment(NotesEditFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNotes(MyNotes myNotes) {
                        data.addNote(myNotes);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToLastPosition = true;
                    }
                });
                return true ;
            case R.id.action_clear:
                data.clearNote();
                adapter.notifyDataSetChanged();
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

        int position = adapter.getMenuPosition()    ;

        switch(item.getItemId()) {
            case R.id.action_update:
                navigation.addFragment(NotesEditFragment.newInstance(data.getMyNotes(position)),
                        true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNotes(MyNotes myNotes) {
                        data.updateNote(position, myNotes);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                data.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}