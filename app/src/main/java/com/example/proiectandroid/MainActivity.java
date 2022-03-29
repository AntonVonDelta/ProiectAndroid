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

    private ArrayList<String> storedImagesUrl=new ArrayList<>();
    private ArrayList<String> storedImagesText=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");

        initImageBitmaps();
    }

    private void initImageBitmaps(){
        storedImagesUrl.add("https://media.gettyimages.com/photos/piazza-san-marco-with-the-basilica-of-saint-mark-and-the-bell-tower-picture-id1063633692?k=20&m=1063633692&s=612x612&w=0&h=Um0CwuTZ3c-jSe9VzRYJL2l2meFO0ah8rxHCz2HTgtc=");
        storedImagesText.add("Town 1");

        storedImagesUrl.add("https://media.gettyimages.com/photos/dam-square-in-amsterdam-a-town-square-in-the-dutch-capital-dam-square-picture-id1140805773?k=20&m=1140805773&s=612x612&w=0&h=AvYXPlBTszvjQzmwfrE9kgl2LicOdBhTL2uPSnh1bJw=");
        storedImagesText.add("Town 2");

        storedImagesUrl.add("https://media.gettyimages.com/photos/overcrowded-with-thousands-of-tourists-and-visitors-during-the-in-picture-id1156013362?k=20&m=1156013362&s=612x612&w=0&h=AEdCFU5gE_Ya5MjrRRnB92jl3Wix6O5gqSxclEwPknM=");
        storedImagesText.add("Town 3");

        storedImagesUrl.add("https://media.gettyimages.com/photos/skiathos-town-on-april-25-2021-in-skiathos-greece-picture-id1314717137?k=20&m=1314717137&s=612x612&w=0&h=Dm0BsbRkybvc6T0IkFtyRc8N1cea-9gbWNdEWV12BVs=");
        storedImagesText.add("Town 4");
//
//        storedImagesUrl.add("https://assets3.thrillist.com/v1/image/2882846/1200x630/flatten;crop_down;webp=auto;jpeg_quality=70");
//        storedImagesText.add("Town 5");
//
//        storedImagesUrl.add("https://travel.usnews.com/dims4/USNEWS/79693dc/2147483647/resize/445x280%5E%3E/crop/445x280/quality/85/?url=https://travel.usnews.com/images/GettyImages-169953764_929f2hd.jpg");
//        storedImagesText.add("Town 6");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView view=findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(storedImagesText,storedImagesUrl,this);

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
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}