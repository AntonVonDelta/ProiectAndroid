package com.example.proiectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;


    private ArrayList<RecyclerViewAdapter.EntryData> storedImagesData=new ArrayList<>();
    private RecyclerView view;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");

        // Rebind toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize the data
        initImageBitmaps();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void initImageBitmaps(){
        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://media.gettyimages.com/photos/piazza-san-marco-with-the-basilica-of-saint-mark-and-the-bell-tower-picture-id1063633692?k=20&m=1063633692&s=612x612&w=0&h=Um0CwuTZ3c-jSe9VzRYJL2l2meFO0ah8rxHCz2HTgtc=",
                "Town 1"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://media.gettyimages.com/photos/dam-square-in-amsterdam-a-town-square-in-the-dutch-capital-dam-square-picture-id1140805773?k=20&m=1140805773&s=612x612&w=0&h=AvYXPlBTszvjQzmwfrE9kgl2LicOdBhTL2uPSnh1bJw=",
                "Town 2"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://media.gettyimages.com/photos/overcrowded-with-thousands-of-tourists-and-visitors-during-the-in-picture-id1156013362?k=20&m=1156013362&s=612x612&w=0&h=AEdCFU5gE_Ya5MjrRRnB92jl3Wix6O5gqSxclEwPknM=",
                "Town 3"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://media.gettyimages.com/photos/skiathos-town-on-april-25-2021-in-skiathos-greece-picture-id1314717137?k=20&m=1314717137&s=612x612&w=0&h=Dm0BsbRkybvc6T0IkFtyRc8N1cea-9gbWNdEWV12BVs=",
                "Town 4"));


        initRecyclerView();
    }

    private void initRecyclerView(){
        view=findViewById(R.id.recyclerview);
        adapter=new RecyclerViewAdapter(storedImagesData,this);

        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView search= (SearchView) item.getActionView();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_add_travel_point:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TravelPlanningFragment()).commit();
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MapFragment()).commit();
                break;
        }
        return true;
    }
}