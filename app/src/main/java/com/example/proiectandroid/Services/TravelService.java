package com.example.proiectandroid.Services;

import com.example.proiectandroid.Adapters.EntryData;
import com.example.proiectandroid.Adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TravelService {
    private static TravelService instance;
    private List<String> destinations=new ArrayList<>();

    private TravelService(){

    }

    public static TravelService getInstance(){
        if(instance==null){
            instance=new TravelService();
        }
        return instance;
    }

    public void addDestination(String location){
        destinations.add(location);
    }

    public void removeDestination(String location){
        destinations.removeIf(el->el.toLowerCase(Locale.ROOT).equals(location.toLowerCase(Locale.ROOT)));
    }

    public List<String> getDestinations(){
        return destinations;
    }

    public ArrayList<EntryData> getDestinationsAsEntryData(){
        LocationsService locationsService=LocationsService.getInstance();
        return (ArrayList<EntryData>) destinations.stream().map(el->locationsService.getDataFromLocation(el).get()).collect(Collectors.toList());
    }

}
