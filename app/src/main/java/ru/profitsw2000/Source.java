package ru.profitsw2000;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import ru.profitsw2000.notes.R;

public class Source implements CardSource {

    private List<MyNotes> dataSource;
    private Resources resources;

    public Source(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public Source init(){
        String[] titles = resources.getStringArray(R.array.notes_title);
        int[] pictures = getImageArray();
        String[] descriptions = resources.getStringArray(R.array.notes_description) ;
        String[] dates = resources.getStringArray(R.array.notes_date) ;
        String[] text = resources.getStringArray(R.array.notes_text)    ;

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new MyNotes(titles[i], pictures[i], descriptions[i], dates[i], text[i]));
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
}
