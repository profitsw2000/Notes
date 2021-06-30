package ru.profitsw2000.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import ru.profitsw2000.MainActivity;
import ru.profitsw2000.data.MyNotes;
import ru.profitsw2000.nav.Publisher;
import ru.profitsw2000.notes.R;

public class NotesEditFragment extends Fragment {

    public final static String ARG_PARAM1 = "MyNotesData"   ;

    private MyNotes myNotes ;
    private Publisher publisher ;

    private TextInputEditText title   ;
    private TextInputEditText description   ;
    private TextInputEditText text   ;
    private DatePicker datePicker   ;

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
        View view = inflater.inflate(R.layout.fragment_notes_edit, container, false);
        initView(view)  ;
        if (myNotes != null) {
            fillView()  ;
        }
        return view ;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context   ;
        publisher = activity.getPublisher() ;
    }

    @Override
    public void onStop() {
        super.onStop();
        myNotes = collectNoteData() ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(myNotes);
    }

    @Override
    public void onDetach() {
        publisher = null    ;
        super.onDetach();
    }

    private void fillView() {
        title.setText(myNotes.getTitle());
        description.setText(myNotes.getDescription());
        text.setText(myNotes.getText());
        initDatePicker(myNotes.getDate())   ;
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle)  ;
        description = view.findViewById(R.id.inputDescription)  ;
        text = view.findViewById(R.id.inputText)    ;
        datePicker = view.findViewById(R.id.inputDate)  ;
    }

    private MyNotes collectNoteData() {
        String title = this.title.getText().toString()  ;
        String description = this.description.getText().toString()  ;
        String text = this.text.getText().toString()    ;

        Date date = getDateFromDatePicker() ;
        int picture ;

        if (myNotes != null) {
            picture = myNotes.getPicture()  ;
        } else {
            picture = R.drawable.code   ;
        }

        return new MyNotes(title, picture, description, date, text)  ;
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance()  ;
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                             calendar.get(Calendar.MONTH),
                             calendar.get(Calendar.DAY_OF_MONTH),
                             null);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }
}