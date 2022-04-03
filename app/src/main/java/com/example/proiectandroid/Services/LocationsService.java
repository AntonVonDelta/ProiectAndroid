package com.example.proiectandroid.Services;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.sqlite.SQLiteDatabase;

import com.example.proiectandroid.Adapters.EntryData;
import com.example.proiectandroid.Adapters.ExtendedData;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocationsService {
    private static LocationsService instance;
    private ArrayList<ExtendedData> storedImagesData = new ArrayList<>();

    private LocationsService() {
        storedImagesData.add(new ExtendedData(
                "https://cdn.getyourguide.com/img/tour/5d9f061aefc81.jpeg/98.jpg",
                "Bucuresti",
                "http://93.161.97.219:81/videostream.cgi?user=admin&pwd="));

        storedImagesData.add(new ExtendedData(
                "https://cdn.getyourguide.com/img/tour/5a4b620bcf7b1.jpeg/146.jpg",
                "Constanta",
                "http://188.193.89.13:8081/videostream.cgi?user=admin&pwd="));

        storedImagesData.add(new ExtendedData(
                "https://cdn.getyourguide.com/img/tour/576be6183665f.jpeg/98.jpg",
                "Venetia",
                "http://193.250.33.191:8080/videostream.cgi?user=admin&pwd="));

        storedImagesData.add(new ExtendedData(
                "https://cdn.getyourguide.com/img/tour/6192d8e53052b.jpeg/145.jpg",
                "Buzau",
                "http://77.129.169.159:85/videostream.cgi?user=admin&pwd="));

        storedImagesData.add(new ExtendedData(
                "https://cdn.getyourguide.com/img/tour/514c74ece075f.jpeg/98.jpg",
                "Cluj",
                ""));

        storedImagesData.add(new ExtendedData(
                "https://cdn.getyourguide.com/img/tour/617f4ef62f911.jpeg/98.jpg",
                "Miami",
                ""));
    }

    public static LocationsService getInstance() {
        if (instance == null) {
            instance = new LocationsService();
        }
        return instance;
    }

    public Optional<EntryData> getDataFromLocation(String location) {
        return storedImagesData.stream().filter(el -> el.Name.equalsIgnoreCase(location)).map(el->new EntryData(el.ImageUrl,el.Name)).findFirst();
    }

    public ArrayList<EntryData> getAllLocations(){
        return (ArrayList<EntryData>) storedImagesData.stream().map(el->new EntryData(el.ImageUrl,el.Name)).collect(Collectors.toList());
    }

    public Optional<String> getVideoUrlFromLocation(String location){
        return storedImagesData.stream().filter(el->el.Name.toLowerCase(Locale.ROOT).contains(location.toLowerCase(Locale.ROOT))).map(el->el.VideoUrl).findFirst();
    }
}
