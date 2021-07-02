package ru.profitsw2000.data;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.profitsw2000.notes.R;

public class Source implements CardSource {

    private List<MyNotes> dataSource;
    private Resources resources;

    public Source(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public Source init(CardSourceResponse cardSourceResponse) throws ParseException {
        String[] titles = resources.getStringArray(R.array.notes_title);
        int[] pictures = getImageArray();
        String[] descriptions = resources.getStringArray(R.array.notes_description) ;
        String[] dates = resources.getStringArray(R.array.notes_date) ;
        String[] text = resources.getStringArray(R.array.notes_text)    ;
        Date[] dates1 = new Date[dates.length];

        for (int i = 0; i < titles.length; i++) {
            dates1[i] = new SimpleDateFormat("dd/MM/yyyy").parse(dates[i])  ;
            dataSource.add(new MyNotes(titles[i], pictures[i], descriptions[i], dates1[i], text[i]));
        }

        if (cardSourceResponse != null) {
            cardSourceResponse.initialized(this);
        }

        return this;
    }

    private int[] getImageArray(){
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    @Override
    public MyNotes getMyNotes(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    @Override
    public void deleteNote(int position) {
        dataSource.remove(position) ;
    }

    @Override
    public void updateNote(int position, MyNotes myNotes) {
        dataSource.set(position, myNotes);
    }

    @Override
    public void addNote(MyNotes myNotes) {
        dataSource.add(myNotes) ;
    }

    @Override
    public void clearNote() {
        dataSource.clear();
    }

}
