package com.mishra.sadikshya.jobfinder.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mishra.sadikshya.jobfinder.Api.ApiInterface;
import com.mishra.sadikshya.jobfinder.Model.GitJobModel;
import com.mishra.sadikshya.jobfinder.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.BASE_URL_GIT;
import static com.mishra.sadikshya.jobfinder.Constants.Constant.CLICKED_ID;

public class Git_Description extends AppCompatActivity {
    private TextView title_description, type_description, company_description, location_description, companyurl_description, url_description, how_to_apply_description, created_at_description, descripion_descripion;
    private ImageView org_logo_description;
    Integer job_Id;
    ApiInterface apiInterface;
    List<GitJobModel>gitJobModel;
    private ProgressBar progressBar_description;


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
        progressBar_description=findViewById(R.id.progressBar_description);
        getJobDescription();

    }

    private void getJobDescription() {
        try {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL_GIT)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            apiInterface = retrofit.create(ApiInterface.class);
            Call<List<GitJobModel>> call = apiInterface.getGitJob();
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
                    created_at_description.setText(gitJobModel.get(job_Id).getCreatedAt());
                    descripion_descripion.setText(gitJobModel.get(job_Id).getDescription());
                    String thumbnail= String.valueOf(gitJobModel.get(job_Id).getCompanyLogo());
                    Linkify.addLinks(url_description, Linkify.WEB_URLS);
                    Linkify.addLinks(companyurl_description, Linkify.WEB_URLS);

                    String des= Html.fromHtml(gitJobModel.get(job_Id).getDescription()).toString();
                    descripion_descripion.setText(des);
                    String howtoapply= Html.fromHtml(gitJobModel.get(job_Id).getHowToApply()).toString();
                    how_to_apply_description.setText(howtoapply);


                    if(thumbnail!=null) {

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
                    }else {
                        Glide.with(Git_Description.this).load(R.drawable.ic_launcher_foreground).into(org_logo_description);
                    }
                }

                @Override
                public void onFailure(Call<List<GitJobModel>> call, Throwable t) {

                }
            });

        } catch (IllegalAccessError e) {

        }
    }
}
