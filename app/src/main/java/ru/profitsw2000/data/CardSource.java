package ru.profitsw2000.data;

import java.text.ParseException;

import ru.profitsw2000.data.MyNotes;

public interface CardSource {

    CardSource init(CardSourceResponse cardSourceResponse) throws ParseException;

    MyNotes getMyNotes(int position)    ;

    int size()  ;

    void deleteNote(int position)   ;

    void updateNote(int position, MyNotes myNotes)  ;

    void addNote(MyNotes myNotes)   ;

    void clearNote()    ;
}
