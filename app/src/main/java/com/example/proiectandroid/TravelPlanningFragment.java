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

import com.example.proiectandroid.Adapters.DeletableRecyclerViewAdapter;
import com.example.proiectandroid.Services.TravelService;

public class TravelPlanningFragment extends Fragment {
    private RecyclerView recyclerView;
    private DeletableRecyclerViewAdapter adapter;

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

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                adapter.hideDeleteButton(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.hideDeleteButton(false);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new DeletableRecyclerViewAdapter(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
