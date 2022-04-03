package com.example.proiectandroid.Services;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proiectandroid.Adapters.EntryData;
import com.example.proiectandroid.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TravelService {
    @Entity
    public static class Destinations {
        @PrimaryKey(autoGenerate = true)
        public int id;

        @ColumnInfo(name = "destination")
        public String destination;
    }

    @Dao
    public interface DestinationDao {
        @Query("SELECT * FROM destinations")
        List<Destinations> getAll();

        @Query("SELECT * FROM destinations WHERE id IN (:destinationsIds)")
        List<Destinations> loadAllByIds(int[] destinationsIds);

        @Query("DELETE FROM destinations WHERE destination=(:location)")
        void deleteLocation(String location);

        @Insert
        void insertOne(Destinations destination);

        @Insert
        void insertAll(Destinations... destinations);

        @Delete
        void delete(Destinations user);
    }

    @Database(entities = {Destinations.class}, version = 1)
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract DestinationDao destinationDao();
    }


    private static TravelService instance;
    private AppDatabase db;
    private List<String> destinations = new ArrayList<>();

    private TravelService() {
        db = Room.databaseBuilder(MainActivity.getInstanceApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        // Load from memory
        destinations = db.destinationDao().getAll().stream().map(el -> el.destination).collect(Collectors.toList());
    }

    public static TravelService getInstance() {
        if (instance == null) {
            instance = new TravelService();
        }
        return instance;
    }

    public void addDestination(String location) {
        destinations.add(location);

        // Add to internal memory
        Destinations newDestination=new Destinations();
        newDestination.destination=location;
        db.destinationDao().insertOne(newDestination);
    }

    public void removeDestination(int position) {
        String destination=destinations.get(position);
        destinations.remove(position);

        // Remove from database
        db.destinationDao().deleteLocation(destination);
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public ArrayList<EntryData> getDestinationsAsEntryData() {
        LocationsService locationsService = LocationsService.getInstance();
        return (ArrayList<EntryData>) destinations.stream().map(el -> locationsService.getDataFromLocation(el).get()).collect(Collectors.toList());
    }

}
