package ru.profitsw2000.data;

import ru.profitsw2000.data.MyNotes;

public interface CardSource {

    MyNotes getMyNotes(int position)    ;

    int size()  ;

    void deleteNote(int position)   ;

    void updateNote(int position, MyNotes myNotes)  ;

    void addNote(MyNotes myNotes)   ;

    void clearNote()    ;
}
