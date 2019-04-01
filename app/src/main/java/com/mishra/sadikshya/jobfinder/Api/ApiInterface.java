package com.mishra.sadikshya.jobfinder.Api;

import com.mishra.sadikshya.jobfinder.Model.GitJobModel;
import com.mishra.sadikshya.jobfinder.Model.GovJobModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("search.json")
    Call<List<GovJobModel>> getGovJob(@Query("location")String location);


    @GET("positions.json")
    Call<List<GitJobModel>> getGitJob(@Query("location")String location);

    @GET("search.json")
    Call<List<GovJobModel>> getGovJobProvider(@Query("prodiver")String location);


    @GET("positions.json")
    Call<List<GitJobModel>> getGitJobProvider(@Query("provider")String location);

    @GET("search.json")
    Call<List<GovJobModel>> getGovJobBy(@Query("position")String location);


    @GET("positions.json")
    Call<List<GitJobModel>> getGitJobBy(@Query("position")String location);

}
