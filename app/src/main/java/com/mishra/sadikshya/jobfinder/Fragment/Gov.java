package com.mishra.sadikshya.jobfinder.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mishra.sadikshya.jobfinder.Activities.Gov_Description;
import com.mishra.sadikshya.jobfinder.Adapter.GovJobAdapter;
import com.mishra.sadikshya.jobfinder.Api.ApiInterface;
import com.mishra.sadikshya.jobfinder.Model.GovJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.BASE_URL_GOV;
import static com.mishra.sadikshya.jobfinder.Constants.Constant.CLICKED_ID;

public class Gov extends Fragment implements GovJobAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private List<GovJobModel> govJobModel;
    private GovJobAdapter govJobAdapter;
    String query;
//    private FloatingActionButton searchFab, providerFab, locationFab;
//    private TextView  searchFab_text, providerFab_text, locationFab_text;
//    Animation fabOpen, fabClose, fabAntiClock, fabClock;
//    boolean isOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_git, container, false);
//        searchFab=view.findViewById(R.id.fab_search);
//        providerFab=view.findViewById(R.id.fab_provider);
//        locationFab=view.findViewById(R.id.fab_location);
//        searchFab_text=view.findViewById(R.id.fab_search_text);
//        providerFab_text=view.findViewById(R.id.fab_provider_text);
//        locationFab_text=view.findViewById(R.id.fab_location_text);
        recyclerView = view.findViewById(R.id.gov_job);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        getJobInfo();

//        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
//        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
//        fabAntiClock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_aniclockwise);
//        fabClock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_clockwise);
//        searchFab.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onClick(View v) {
//                if (isOpen) {
//                    locationFab.startAnimation(fabClose);
//                    providerFab_text.startAnimation(fabClose);
//                    locationFab_text.startAnimation(fabClose);
//                    providerFab.startAnimation(fabClose);
//                    searchFab.startAnimation(fabAntiClock);
//                    locationFab.setClickable(false);
//                    providerFab.setClickable(false);
//                    isOpen = false;
//
//                } else {
//                    providerFab.startAnimation(fabOpen);
//                    locationFab.startAnimation(fabOpen);
//                    searchFab.startAnimation(fabClock);
//                    locationFab_text.startAnimation(fabOpen);
//                    providerFab_text.startAnimation(fabOpen);
//                    searchFab.setClickable(true);
//                    providerFab.setClickable(true);
//                    providerFab.setVisibility(View.VISIBLE);
//                    locationFab.setVisibility(View.VISIBLE);
//                    providerFab_text.setVisibility(View.VISIBLE);
//                    locationFab_text.setVisibility(View.VISIBLE);
//
//                    isOpen = true;
//                }
//
//            }
//        });
//        locationFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               startActivity(new Intent(getContext(), SearchJob.class));
//            }
//        });
//        providerFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), SearchJob.class));
//            }
//        });
        return view;
    }


    private void getJobInfo() {
        try {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL_GOV)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            apiInterface = retrofit.create(ApiInterface.class);
            Call<List<GovJobModel>> call = apiInterface.getGovJob(query);
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
