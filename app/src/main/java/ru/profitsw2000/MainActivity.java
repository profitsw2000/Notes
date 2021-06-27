package ru.profitsw2000;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import ru.profitsw2000.notes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar()   ;
        initDrawer(toolbar) ;
        addFragment(NotesTitleFragment.newInstance())   ;
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager()   ;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()    ;
        fragmentTransaction.replace(R.id.notes_title, fragment) ;
        fragmentTransaction.addToBackStack(null)    ;
        fragmentTransaction.commit()    ;
    }

    private void initDrawer(Toolbar toolbar) {

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem item) {
                 int id = item.getItemId();

                 switch(id){
                     case R.id.about:
                         Toast.makeText(getApplicationContext(),"About...", Toast.LENGTH_SHORT).show();  ;
                         return true;
                     case R.id.action_main:
                         Toast.makeText(getApplicationContext(),"Main Page", Toast.LENGTH_SHORT).show();
                         return true;
                     case R.id.action_history:
                         Toast.makeText(getApplicationContext(),"History", Toast.LENGTH_SHORT).show();  ;
                         return true;
                 }
                 return false;
             }
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar)    ;
        setSupportActionBar(toolbar);
        return toolbar  ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast ;

        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                toast = Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT)   ;
                toast.show();
                return true;
            case R.id.action_favorite:
                toast = Toast.makeText(getApplicationContext(),"Favorite", Toast.LENGTH_SHORT)   ;
                toast.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query,
                        Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

}