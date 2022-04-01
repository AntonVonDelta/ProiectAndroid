package com.example.proiectandroid;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TravelPlanningFragment extends Fragment {
    private ArrayList<RecyclerViewAdapter.EntryData> storedImagesData = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Signal that we have "options"
        setHasOptionsMenu(true);

        // Inflate view
        view=inflater.inflate(R.layout.fragment_travel_planning, container, false);

        // Initialize the data
        initImageBitmaps();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initImageBitmaps() {
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

    private void initRecyclerView() {
        recyclerView =view.findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(storedImagesData, getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
