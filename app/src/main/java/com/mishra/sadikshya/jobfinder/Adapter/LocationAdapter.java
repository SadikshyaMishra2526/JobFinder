package com.mishra.sadikshya.jobfinder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mishra.sadikshya.jobfinder.Model.GovJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.RecyclerViewHolderClass> {
    Context context;
    List<GovJobModel> locations;



    public LocationAdapter(Context context, List<GovJobModel> locations) {
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationAdapter.RecyclerViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gov_job_location, viewGroup, false);
        return new LocationAdapter.RecyclerViewHolderClass(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final LocationAdapter.RecyclerViewHolderClass holder, int position) {

        holder.location.setText((CharSequence) locations.get(position).getLocations().get(position));

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }




    public class RecyclerViewHolderClass extends RecyclerView.ViewHolder {
        TextView location;


        public RecyclerViewHolderClass(@NonNull View itemView) {
            super(itemView);
            location=itemView.findViewById(R.id.location);

        }
    }
}