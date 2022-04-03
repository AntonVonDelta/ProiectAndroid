package com.example.proiectandroid.Services;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Patterns;

import com.example.proiectandroid.Adapters.EntryData;
import com.example.proiectandroid.Adapters.ExtendedData;
import com.example.proiectandroid.Events.EventChange;
import com.google.gson.annotations.SerializedName;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class LocationsService {
    public class GitHubRepo {
        private String name;

        public String getName() {
            return name;
        }
    }

    public class GithubBranch {
        private String name;
        private Commit commit;

        @SerializedName("protected")
        private String protectedField;


        public String getName() {
            return name;
        }

        public Commit getCommit() {
            return commit;
        }

        public String getProtectedField() {
            return protectedField;
        }

        public class Commit {
            private String sha;
            private String url;
        }
    }

    public interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<GitHubRepo>> listRepos(@Path("user") String user);

        @GET("repos/AntonVonDelta/ProiectAndroid/branches")
        Call<List<GithubBranch>> listBranches();

        @GET("AntonVonDelta/ProiectAndroid/{commit}/Locations/locations.txt")
        Call<String> getRawFile(@Path("commit") String commit);
    }

    private static LocationsService instance;
    private ArrayList<ExtendedData> storedImagesData = new ArrayList<>();
    private List<EventChange> listener = new ArrayList<EventChange>();


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


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit retrofitRawGit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        GitHubService serviceRawGit = retrofitRawGit.create(GitHubService.class);


        Call<List<GithubBranch>> call = service.listBranches();
        call.enqueue(new Callback<List<GithubBranch>>() {
            @Override
            public void onResponse(Call<List<GithubBranch>> call, Response<List<GithubBranch>> response) {
                Call<String> call2 = serviceRawGit.getRawFile(response.body().get(0).getCommit().sha);
                call2.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        parseNetFile(response.body());
                        notifyListeners();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<GithubBranch>> call, Throwable t) {

            }
        });


        service.listRepos("AntonVonDelta");
    }

    public static LocationsService getInstance() {
        if (instance == null) {
            instance = new LocationsService();
        }
        return instance;
    }

    public Optional<EntryData> getDataFromLocation(String location) {
        return storedImagesData.stream().filter(el -> el.Name.equalsIgnoreCase(location)).map(el -> new EntryData(el.ImageUrl, el.Name)).findFirst();
    }

    public ArrayList<EntryData> getAllLocations() {
        return (ArrayList<EntryData>) storedImagesData.stream().map(el -> new EntryData(el.ImageUrl, el.Name)).collect(Collectors.toList());
    }

    public Optional<String> getVideoUrlFromLocation(String location) {
        return storedImagesData.stream().filter(el -> el.Name.toLowerCase(Locale.ROOT).contains(location.toLowerCase(Locale.ROOT))).map(el -> el.VideoUrl).findFirst();
    }


    private void parseNetFile(String data) {
        Pattern pattern = Pattern.compile("(.+)\\n(.+)\\n(.+)\\n");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            storedImagesData.add(new ExtendedData(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3)));
        }
    }

    private void notifyListeners() {
        for (EventChange name : listener) {
            name.eventChange(getAllLocations());
        }
    }

    public void addChangeListener(EventChange newListener) {
        listener.add(newListener);
    }
}
