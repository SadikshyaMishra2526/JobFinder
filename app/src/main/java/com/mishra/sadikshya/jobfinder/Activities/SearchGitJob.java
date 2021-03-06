package com.mishra.sadikshya.jobfinder.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Fade;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

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

public class SearchGitJob extends AppCompatActivity implements GitJobAdapter.OnItemClickListener{
    ApiInterface apiInterface;
    private List<GitJobModel> gitJobModel;
    private GitJobAdapter gitJobAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    String query,position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        recyclerView = findViewById(R.id.gov_job);
        layoutManager = new GridLayoutManager(SearchGitJob.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("From Submit");
                query=s;
                position=s;
                forJob();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                query=s;
                position=s;
                forJob();
                return false;
            }
        });

        if(query!=null ||position!=null){
            forJob();
        }else {
            Toast.makeText(this, "Please Enter Your Search", Toast.LENGTH_SHORT).show();
        }



        return super.onCreateOptionsMenu(menu);
    }

    private void forJob() {
        try {
            apiInterface = Git_Client.createGitService(ApiInterface.class);
            Call<List<GitJobModel>> call = apiInterface.getGitJob(query,position);

            call.enqueue(new Callback<List<GitJobModel>>() {
                @Override
                public void onResponse(Call<List<GitJobModel>> call, Response<List<GitJobModel>> response) {
                    gitJobModel = response.body();
                    if (this != null) {
                        gitJobAdapter = new GitJobAdapter(SearchGitJob.this,  gitJobModel);
                        recyclerView.setAdapter(gitJobAdapter);
                        gitJobAdapter.setOnItemClickListener(SearchGitJob.this);

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
        Intent intent = new Intent(SearchGitJob.this, Git_Description.class);
        intent.putExtra(CLICKED_ID, position);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
           getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setExitTransition(new Fade(Fade.OUT));
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SearchGitJob.this, Pair.create(itemView.findViewById(R.id.cardView_git), "preferred"));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
