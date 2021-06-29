package ru.profitsw2000.data;

import ru.profitsw2000.data.MyNotes;

public interface CardSource {
    MyNotes getMyNotes(int position)    ;
    int size()  ;
}
