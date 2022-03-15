package com.example.proiectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

    }

    private void initImageBitmaps(){
        storedImagesUrl.add("https://media.farandwide.com/bc/ea/bceaf71ef0c54deea9ca2ed580099c23.jpeg");
        storedImagesText.add("Town 1");

        storedImagesUrl.add("https://hips.hearstapps.com/clv.h-cdn.co/assets/16/19/1463066333-gettyimages-106523664.jpg");
        storedImagesText.add("Town 2");

        storedImagesUrl.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/bridge-over-delaware-river-royalty-free-image-155438790-1531171842.jpg?crop=1.00xw:0.754xh;0,0&resize=480:*");
        storedImagesText.add("Town 3");

        storedImagesUrl.add("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F28%2F2021%2F05%2F07%2Flead-telluride-COLORADOSUMMER0521.jpg");
        storedImagesText.add("Town 4");

        storedImagesUrl.add("https://assets3.thrillist.com/v1/image/2882846/1200x630/flatten;crop_down;webp=auto;jpeg_quality=70");
        storedImagesText.add("Town 5");

        storedImagesUrl.add("https://travel.usnews.com/dims4/USNEWS/79693dc/2147483647/resize/445x280%5E%3E/crop/445x280/quality/85/?url=https://travel.usnews.com/images/GettyImages-169953764_929f2hd.jpg");
        storedImagesText.add("Town 6");
    }

    private void initRecyclerView(){
        RecyclerView view=findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(storedImagesText,storedImagesUrl,this);

        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));
    }
}