package com.mishra.sadikshya.jobfinder.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mishra.sadikshya.jobfinder.Model.GitJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

public class GitJobAdapter  extends RecyclerView.Adapter<GitJobAdapter.RecyclerViewHolderClass> {
    Context context;
    List<GitJobModel> gitJobModel;
    private GitJobAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
    }

    public void setOnItemClickListener(GitJobAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public GitJobAdapter(Context context, List<GitJobModel> gitJobModel) {
        this.context = context;
        this.gitJobModel = gitJobModel;
    }

    @NonNull
    @Override
    public GitJobAdapter.RecyclerViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_git_job, viewGroup, false);
        return new GitJobAdapter.RecyclerViewHolderClass(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final GitJobAdapter.RecyclerViewHolderClass holder, int position) {
        holder.org_name.setText((CharSequence) gitJobModel.get(position).getCompany());
        holder.location.setText((CharSequence) gitJobModel.get(position).getLocation());
        holder.created_At.setText((CharSequence) gitJobModel.get(position).getCreatedAt());
        holder.title.setText((CharSequence) gitJobModel.get(position).getTitle());
        String thumbnail = (String) gitJobModel.get(position).getCompanyLogo();
        if(thumbnail!=null) {

            holder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(context)

                    .load(thumbnail)
                    .apply(new RequestOptions().centerCrop())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;

                        }
                    })
                    .into(holder.org_logo);
        }else {
            Glide.with(context).load(R.drawable.ic_launcher_foreground).into(holder.org_logo);
        }
    }

    @Override
    public int getItemCount() {
        return gitJobModel.size();
    }




    public class RecyclerViewHolderClass extends RecyclerView.ViewHolder {
        TextView title,location,created_At,org_name;
        ImageView org_logo;
        ProgressBar progressBar;

        public RecyclerViewHolderClass(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.org_title);
            location=itemView.findViewById(R.id.org_location);
            created_At=itemView.findViewById(R.id.job_created_at);
            org_name=itemView.findViewById(R.id.org_name);
            org_logo=itemView.findViewById(R.id.org_logo);
            progressBar=itemView.findViewById(R.id.git_progress_bar);



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
