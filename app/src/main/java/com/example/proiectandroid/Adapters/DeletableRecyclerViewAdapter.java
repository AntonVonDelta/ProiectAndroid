package com.example.proiectandroid.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proiectandroid.OnItemClicked;
import com.example.proiectandroid.R;
import com.example.proiectandroid.Services.TravelService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeletableRecyclerViewAdapter extends RecyclerView.Adapter<DeletableRecyclerViewAdapter.ViewHolder> implements Filterable {
    private ArrayList<EntryData> storedImageData = new ArrayList<EntryData>();

    private Context context;
    private TravelService travelService;
    private boolean hideDeleteButton=false;

    public DeletableRecyclerViewAdapter(Context context) {
        travelService=TravelService.getInstance();

        this.storedImageData = travelService.getDestinationsAsEntryData();
        this.context = context;
        travelService=TravelService.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deletablelistitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context)
                .asBitmap()
                .load(storedImageData.get(position).ImageUrl)
                .into(holder.image);

        holder.imageText.setText(storedImageData.get(position).Name);

        // Hide the delete button if requested
        if(hideDeleteButton){
            holder.deleteView.setVisibility(View.INVISIBLE);
        }else{
            holder.deleteView.setVisibility(View.VISIBLE);
        }

        // Handle click event
        holder.deleteView.setOnClickListener(view -> {
            // Remove location from global service
            travelService.removeDestination(position);

            // Update internal state
            storedImageData = travelService.getDestinationsAsEntryData();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return storedImageData.size();
    }

    @Override
    public Filter getFilter() {
        ArrayList<EntryData> storedAllImageData = new ArrayList<>(travelService.getDestinationsAsEntryData());

        Filter customFilter = new Filter() {
            // This seems to run in the back
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<EntryData> result = new ArrayList<>();

                if (charSequence.toString().isEmpty()) {
                    // Show all items and do not filter any
                    result = storedAllImageData;
                } else {
                    result = storedAllImageData.stream()
                            .filter((e) -> e.Name.toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT)))
                            .collect(Collectors.toList());
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = result;
                return filterResults;
            }

            // This seems to run in the ui
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                storedImageData.clear();
                storedImageData.addAll((Collection<? extends EntryData>) filterResults.values);
                notifyDataSetChanged();
            }
        };

        return customFilter;
    }

    public void hideDeleteButton(boolean flag){
        hideDeleteButton=flag;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView imageText;
        RelativeLayout itemLayout;
        ImageView deleteView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            imageText = itemView.findViewById(R.id.imageText);
            itemLayout = itemView.findViewById(R.id.layout_parent);
            deleteView = itemView.findViewById(R.id.delete);
        }
    }

}
