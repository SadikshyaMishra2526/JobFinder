package com.mishra.sadikshya.jobfinder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mishra.sadikshya.jobfinder.Model.GovJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

public class GovJobAdapter extends RecyclerView.Adapter<GovJobAdapter.RecyclerViewHolderClass> {
    Context context;
    List<GovJobModel> govJobModel;
    private GovJobAdapter.OnItemClickListener mListener;
    String imageUrl;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
    }

    public void setOnItemClickListener(GovJobAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public GovJobAdapter(Context context, List<GovJobModel> govJobModel) {
        this.context = context;
        this.govJobModel = govJobModel;
    }

    @NonNull
    @Override
    public RecyclerViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gov_job, viewGroup, false);
        return new RecyclerViewHolderClass(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolderClass holder, int position) {

        holder.org_name.setText((CharSequence) govJobModel.get(position).getOrganizationName());
        holder.applying_url.setText((CharSequence) govJobModel.get(position).getUrl());
        holder.created_At.setText((CharSequence) govJobModel.get(position).getStartDate());
        holder.title.setText((CharSequence) govJobModel.get(position).getPositionTitle());
        Linkify.addLinks(holder.applying_url, Linkify.WEB_URLS);

    }

    @Override
    public int getItemCount() {
        return govJobModel.size();
    }




    public class RecyclerViewHolderClass extends RecyclerView.ViewHolder {
    TextView title,applying_url,created_At,org_name;


        public RecyclerViewHolderClass(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.org_title);
            applying_url=itemView.findViewById(R.id.org_applying_url);
            created_At=itemView.findViewById(R.id.job_created_at);
            org_name=itemView.findViewById(R.id.org_name);



            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            mListener.onItemClick(position, view);
                        }
                    }
                }
            });



        }
    }
}