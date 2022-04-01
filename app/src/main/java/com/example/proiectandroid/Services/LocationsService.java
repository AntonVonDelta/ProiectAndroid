package com.example.proiectandroid.Services;

import com.example.proiectandroid.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationsService {
    private static LocationsService instance;
    private ArrayList<RecyclerViewAdapter.EntryData> storedImagesData = new ArrayList<>();



    private LocationsService(){
        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://cdn.getyourguide.com/img/tour/5d9f061aefc81.jpeg/98.jpg",
                "Bucuresti"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://cdn.getyourguide.com/img/tour/5a4b620bcf7b1.jpeg/146.jpg",
                "Constanta"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://cdn.getyourguide.com/img/tour/576be6183665f.jpeg/98.jpg",
                "Venetia"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://cdn.getyourguide.com/img/tour/6192d8e53052b.jpeg/145.jpg",
                "Brasov"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://cdn.getyourguide.com/img/tour/514c74ece075f.jpeg/98.jpg",
                "Cluj"));

        storedImagesData.add(new RecyclerViewAdapter.EntryData(
                "https://cdn.getyourguide.com/img/tour/617f4ef62f911.jpeg/98.jpg",
                "Miami"));
    }

    public static LocationsService getInstance() {
        if(instance==null){
            instance=new LocationsService();
        }
        return instance;
    }

    public Optional<RecyclerViewAdapter.EntryData> getDataFromLocation(String location){
        return storedImagesData.stream().filter(el->el.Name.equalsIgnoreCase(location)).findFirst();
    }

}
