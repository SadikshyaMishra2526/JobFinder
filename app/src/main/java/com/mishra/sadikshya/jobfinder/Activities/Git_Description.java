package com.mishra.sadikshya.jobfinder.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mishra.sadikshya.jobfinder.Api.ApiInterface;
import com.mishra.sadikshya.jobfinder.Constants.Git_Client;
import com.mishra.sadikshya.jobfinder.Model.GitJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.CLICKED_ID;

public class Git_Description extends AppCompatActivity {
    private TextView title_description, type_description, company_description, location_description, companyurl_description, url_description, how_to_apply_description, created_at_description, descripion_descripion;
    private ImageView org_logo_description;
    Integer job_Id;
    ApiInterface apiInterface;
    List<GitJobModel> gitJobModel;
    private ProgressBar progressBar_description;

    String query,position;
    CharSequence createdAt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.git_job_description);
        job_Id = getIntent().getIntExtra(CLICKED_ID, 0);
        title_description = findViewById(R.id.job_title_description);
        type_description = findViewById(R.id.job_type_description);
        company_description = findViewById(R.id.company_name_description);
        location_description = findViewById(R.id.job_location_description);
        url_description = findViewById(R.id.job_url_description);
        companyurl_description = findViewById(R.id.company_url_description);
        how_to_apply_description = findViewById(R.id.how_to_apply_description);
        created_at_description = findViewById(R.id.created_at_description);
        descripion_descripion = findViewById(R.id.job_description_description);
        org_logo_description = findViewById(R.id.org_logo_description);
        progressBar_description = findViewById(R.id.progressBar_description);
        Date d = new Date();
        createdAt = DateFormat.format("MMMM d, yyyy ", d.getTime());
        getJobDescription();

    }

    private void getJobDescription() {
        try {
            apiInterface = Git_Client.createGitService(ApiInterface.class);
            Call<List<GitJobModel>> call = apiInterface.getGitJob(query,position);
            call.enqueue(new Callback<List<GitJobModel>>() {
                @Override
                public void onResponse(Call<List<GitJobModel>> call, Response<List<GitJobModel>> response) {
                    gitJobModel = response.body();
                    title_description.setText(gitJobModel.get(job_Id).getTitle());
                    type_description.setText(gitJobModel.get(job_Id).getType());
                    company_description.setText(gitJobModel.get(job_Id).getCompany());
                    location_description.setText(gitJobModel.get(job_Id).getLocation());
                    url_description.setText(gitJobModel.get(job_Id).getUrl());
                    companyurl_description.setText((CharSequence) gitJobModel.get(job_Id).getCompanyUrl());
                    how_to_apply_description.setText(gitJobModel.get(job_Id).getHowToApply());
                    if (gitJobModel.get(job_Id).getCreatedAt() != null) {
                        created_at_description.setText(gitJobModel.get(job_Id).getCreatedAt());
                    } else {
                        created_at_description.setText(createdAt);
                    }
                    descripion_descripion.setText(gitJobModel.get(job_Id).getDescription());
                    String thumbnail = String.valueOf(gitJobModel.get(job_Id).getCompanyLogo());
                    Linkify.addLinks(url_description, Linkify.WEB_URLS);
                    Linkify.addLinks(companyurl_description, Linkify.WEB_URLS);

                    String des = Html.fromHtml(gitJobModel.get(job_Id).getDescription()).toString();
                    descripion_descripion.setText(des);
                    String howtoapply = Html.fromHtml(gitJobModel.get(job_Id).getHowToApply()).toString();
                    how_to_apply_description.setText(howtoapply);


                    if (thumbnail == null) {
                        Glide.with(Git_Description.this).load(R.drawable.index).into(org_logo_description);
                        } else {
                        progressBar_description.setVisibility(View.VISIBLE);
                        Glide.with(Git_Description.this)
                                .load(thumbnail)
                                .apply(new RequestOptions().centerCrop())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        progressBar_description.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progressBar_description.setVisibility(View.GONE);
                                        return false;

                                    }
                                })
                                .into(org_logo_description);

                        }
                    }

                    @Override
                    public void onFailure (Call < List<GitJobModel >> call, Throwable t){
                        Toast.makeText(Git_Description.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch(IllegalAccessError e){

            }
        }
    }
