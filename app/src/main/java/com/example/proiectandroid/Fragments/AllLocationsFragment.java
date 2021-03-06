package com.example.proiectandroid.Fragments;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectandroid.Adapters.StaticRecyclerViewAdapter;
import com.example.proiectandroid.Events.EventChange;
import com.example.proiectandroid.MainActivity;
import com.example.proiectandroid.OnItemClicked;
import com.example.proiectandroid.R;
import com.example.proiectandroid.Services.LocationsService;
import com.example.proiectandroid.Services.TravelService;

import java.util.Random;

public class AllLocationsFragment extends Fragment implements OnItemClicked {
    private RecyclerView recyclerView;
    private StaticRecyclerViewAdapter adapter;

    private View view;
    private LocationsService locationsService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Signal that we have "options"
        setHasOptionsMenu(true);

        // Load all locations service
        locationsService = LocationsService.getInstance();
        locationsService.addChangeListener(new EventChange() {
            @Override
            public void eventChange(Object data) {
                // Recreate the view and adapter
                initRecyclerView();
            }
        });
        // Inflate view
        view = inflater.inflate(R.layout.fragment_all_locations, container, false);

        // Initialize the data
        initRecyclerView();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView search = (SearchView) item.getActionView();

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
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new StaticRecyclerViewAdapter(locationsService.getAllLocations(), getActivity(),this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClicked(int position) {
        TravelService travelService=TravelService.getInstance();
        travelService.addDestination(locationsService.getAllLocations().get(position).Name);

        PendingIntent pendingIntent = new NavDeepLinkBuilder(getContext())
                .setGraph(R.navigation.app_nav)
                .setDestination(R.id.travelPlanningFragment)
                .createPendingIntent();


        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(), MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_map)
                .setContentTitle("Added destination")
                .setContentText(locationsService.getAllLocations().get(position).Name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(new Random().nextInt(), builder.build());
    }
}
