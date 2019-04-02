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

import com.mishra.sadikshya.jobfinder.Activities.Gov_Description;
import com.mishra.sadikshya.jobfinder.Activities.SearchGovJob;
import com.mishra.sadikshya.jobfinder.Adapter.GovJobAdapter;
import com.mishra.sadikshya.jobfinder.Api.ApiInterface;
import com.mishra.sadikshya.jobfinder.Constants.Gov_Client;
import com.mishra.sadikshya.jobfinder.Model.GovJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.CLICKED_ID;

public class Gov extends Fragment implements GovJobAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private List<GovJobModel> govJobModel;
    private GovJobAdapter govJobAdapter;
    String query,position;
    private FloatingActionButton searchFab;
    private TextView searchFab_text;
    Animation fabOpen, fabClose, fabAntiClock, fabClock;
    boolean isOpen = false;

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
                    startActivity(new Intent(getContext(), SearchGovJob.class));
                    searchFab.startAnimation(fabAntiClock);
                    isOpen = false;
                } else {
                    startActivity(new Intent(getContext(), SearchGovJob.class));
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
            apiInterface = Gov_Client.createGovService(ApiInterface.class);
            Call<List<GovJobModel>> call = apiInterface.getGovJob(query,position);
            call.enqueue(new Callback<List<GovJobModel>>() {
                @Override
                public void onResponse(Call<List<GovJobModel>> call, Response<List<GovJobModel>> response) {
                    govJobModel = response.body();
                    if (this != null) {
                        govJobAdapter = new GovJobAdapter(getContext(), govJobModel);
                        recyclerView.setAdapter(govJobAdapter);
                        govJobAdapter.setOnItemClickListener(Gov.this);
                    }
                }

                @Override
                public void onFailure(Call<List<GovJobModel>> call, Throwable t) {

                }
            });

        } catch (IllegalAccessError e) {

        }
    }

    @Override
    public void onItemClick(int position, View itemView) {
        Intent intent = new Intent(getContext(), Gov_Description.class);
        intent.putExtra(CLICKED_ID, position);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setEnterTransition(new Fade(Fade.IN));
            getActivity().getWindow().setExitTransition(new Fade(Fade.OUT));
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create(itemView.findViewById(R.id.cardView_gov), "preferred"));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
