package com.mishra.sadikshya.jobfinder.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mishra.sadikshya.jobfinder.Activities.Git_Description;
import com.mishra.sadikshya.jobfinder.Activities.SearchGitJob;
import com.mishra.sadikshya.jobfinder.Adapter.GitJobAdapter;
import com.mishra.sadikshya.jobfinder.Api.ApiInterface;
import com.mishra.sadikshya.jobfinder.Constants.Git_Client;
import com.mishra.sadikshya.jobfinder.Model.GitJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.CLICKED_ID;

public class GitHub extends Fragment implements GitJobAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private List<GitJobModel> gitJobModel;
    private GitJobAdapter gitJobAdapter;
    private FloatingActionButton searchFab;
    private TextView searchFab_text;
    Animation fabOpen, fabClose, fabAntiClock, fabClock;
    boolean isOpen = false;
    String query,position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_git, container, false);
        searchFab = view.findViewById(R.id.fab_search);
         searchFab_text = view.findViewById(R.id.fab_search_text);
         recyclerView = view.findViewById(R.id.gov_job);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        getJobInfo();

        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fabAntiClock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_aniclockwise);
        fabClock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_clockwise);
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    startActivity(new Intent(getContext(), SearchGitJob.class));
                    searchFab.startAnimation(fabAntiClock);
                    isOpen = false;

                } else {
                    startActivity(new Intent(getContext(), SearchGitJob.class));
                    searchFab.startAnimation(fabClock);
                    searchFab.setClickable(true);
                    isOpen = true;
                }

            }
        });

        return view;
    }

    private void getJobInfo() {
        try {
            apiInterface = Git_Client.createGitService(ApiInterface.class);
            Call<List<GitJobModel>> call = apiInterface.getGitJob(query,position);
            call.enqueue(new Callback<List<GitJobModel>>() {
                @Override
                public void onResponse(Call<List<GitJobModel>> call, Response<List<GitJobModel>> response) {
                    gitJobModel = response.body();
                    if (this != null) {
                        gitJobAdapter = new GitJobAdapter(getContext(), gitJobModel);
                        recyclerView.setAdapter(gitJobAdapter);
                        gitJobAdapter.setOnItemClickListener(GitHub.this);

                    }
                }

                @Override
                public void onFailure(Call<List<GitJobModel>> call, Throwable t) {

                }
            });

        } catch (IllegalAccessError e) {

        }
    }

    @Override
    public void onItemClick(int position, View itemView) {
        Intent intent = new Intent(getContext(), Git_Description.class);
        intent.putExtra(CLICKED_ID, position);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setEnterTransition(new Fade(Fade.IN));
            getActivity().getWindow().setExitTransition(new Fade(Fade.OUT));
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create(itemView.findViewById(R.id.cardView_git), "preferred"));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
