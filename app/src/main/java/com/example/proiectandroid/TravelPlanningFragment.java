package com.example.proiectandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectandroid.Adapters.RecyclerViewAdapter;
import com.example.proiectandroid.Services.LocationsService;
import com.example.proiectandroid.Services.TravelService;

public class TravelPlanningFragment extends Fragment implements OnItemClicked {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private View view;
    private TravelService travelService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Signal that we have "options"
        setHasOptionsMenu(true);

        // Load travel service
        travelService = TravelService.getInstance();

        // Inflate view
        view = inflater.inflate(R.layout.fragment_travel_planning, container, false);

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
        adapter = new RecyclerViewAdapter(travelService.getDestinationsAsEntryData(), getActivity(), this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClicked(int position) {
        travelService.removeDestination(position);
    }
}
