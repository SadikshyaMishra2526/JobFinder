package com.mishra.sadikshya.jobfinder.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.BASE_URL_GIT;

public class Git_Client {
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_GIT)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();
    public static <S> S createGitService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
