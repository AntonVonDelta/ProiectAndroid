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
                "https://s.iw.ro/gateway/g/ZmlsZVNvdXJjZT1odHRwJTNBJTJGJTJG/c3RvcmFnZTA2dHJhbnNjb2Rlci5yY3Mt/cmRzLnJvJTJGc3RvcmFnZSUyRjIwMjAl/MkYwNSUyRjE0JTJGMTE4NTY3NV8xMTg1/Njc1X0dldHR5SW1hZ2VzLTExNDQ2NTMx/MTguanBnJnc9NzgwJmg9NDQwJmhhc2g9/MmIzNzNlZTRkNTQ0MjJmYzUzNTAzMjliNTc0N2U0YmY=.thumb.jpg",
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


        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView =view.findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(storedImagesData, getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
