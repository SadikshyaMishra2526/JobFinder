package com.mishra.sadikshya.jobfinder.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.util.Linkify;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mishra.sadikshya.jobfinder.Adapter.LocationAdapter;
import com.mishra.sadikshya.jobfinder.Api.ApiInterface;
import com.mishra.sadikshya.jobfinder.Constants.Gov_Client;
import com.mishra.sadikshya.jobfinder.Model.GovJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.CLICKED_ID;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Gov_Description extends AppCompatActivity {
    private TextView title_description, max_description, mini_description, company_description, url_description,location_description, created_at_description;
    Integer job_Id;
    ApiInterface apiInterface;
    List<GovJobModel> govJobModel;
    List<String> locationArray;
    private ProgressBar progressBar_description;
    String des = null;
    List<String>loc;
    String query,location;
    CharSequence createdAt;
//    RecyclerView location_description;
    private RecyclerView.LayoutManager layoutManager;
    LocationAdapter locationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gov_job_description);
        job_Id = getIntent().getIntExtra(CLICKED_ID, 0);
        title_description = findViewById(R.id.job_title_description);
        company_description = findViewById(R.id.org_name_description);
        url_description = findViewById(R.id.job_url_description);
        created_at_description = findViewById(R.id.created_at_description);
        max_description = findViewById(R.id.job_max_description);
        mini_description = findViewById(R.id.job_min_description);
        location_description = findViewById(R.id.job_location_description);
        getJobDescription();
        Date d = new Date();
        createdAt  = DateFormat.format("MMMM d, yyyy ", d.getTime());
//        layoutManager = new GridLayoutManager(Gov_Description.this, 1);
//        location_description.setLayoutManager(layoutManager);
//        location_description.setHasFixedSize(true);
//        location_description.setFocusable(false);
    }

    private void getJobDescription() {
        try {
            apiInterface = Gov_Client.createGovService(ApiInterface.class);
            Call<List<GovJobModel>> call = apiInterface.getGovJob(query,location);
            call.enqueue(new Callback<List<GovJobModel>>() {
                @Override
                public void onResponse(Call<List<GovJobModel>> call, Response<List<GovJobModel>> response) {
                    govJobModel = response.body();
                    title_description.setText(govJobModel.get(job_Id).getPositionTitle());
                    company_description.setText(govJobModel.get(job_Id).getOrganizationName());
                    locationArray = govJobModel.get(job_Id).getLocations();
                    for (int i = 0; i < locationArray.size(); i++) {
                        System.out.println(locationArray.get(i));
                        for (int i1 = 0; i1 < locationArray.size(); i1++) {
                            location_description.setText(locationArray.get(i1));
                        }


                    }
//                    locationAdapter = new LocationAdapter(Gov_Description.this, govJobModel);
//                    location_description.setAdapter(locationAdapter);


                    url_description.setText(govJobModel.get(job_Id).getUrl());
                    if(govJobModel.get(job_Id).getStartDate()!=null){
                    created_at_description.setText(govJobModel.get(job_Id).getStartDate());
                    }else{
                        created_at_description.setText(createdAt);
                    }
                    max_description.setText(govJobModel.get(job_Id).getMaximum().toString());
                    mini_description.setText(govJobModel.get(job_Id).getMinimum().toString());
                    Linkify.addLinks(url_description, Linkify.WEB_URLS);


                }

                @Override
                public void onFailure(Call<List<GovJobModel>> call, Throwable t) {

                }
            });

        } catch (IllegalAccessError e) {

        }
    }
}

