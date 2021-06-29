package ru.profitsw2000.nav;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.profitsw2000.notes.R;

public class Navigation {

    private final FragmentManager fragmentManager   ;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager  ;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()    ;
        fragmentTransaction.replace(R.id.notes_title, fragment) ;

        if (useBackStack) {
            fragmentTransaction.addToBackStack(null)    ;
        }
        fragmentTransaction.commit()    ;
    }

    public void clearBackStack() {
        fragmentManager.popBackStackImmediate()    ;
    }
}
