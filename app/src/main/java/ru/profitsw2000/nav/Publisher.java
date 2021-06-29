package ru.profitsw2000.nav;

import java.util.ArrayList;
import java.util.List;

import ru.profitsw2000.data.MyNotes;

public class Publisher {
    private List<Observer> observers    ;

    public Publisher() {
        observers = new ArrayList<>()   ;
    }

    public void subscribe (Observer observer) {
        observers.add(observer) ;
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer)  ;
    }

    public void notifySingle(MyNotes myNotes) {
        for (Observer observer:
             observers) {
            observer.updateNotes(myNotes);
            unsubscribe(observer);
        }
    }
}
