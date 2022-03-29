package com.example.proiectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<RecyclerViewAdapter.EntryData> storedImagesData=new ArrayList<>();
    private RecyclerView view;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");

        initImageBitmaps();
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
}