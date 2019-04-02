package com.mishra.sadikshya.jobfinder.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mishra.sadikshya.jobfinder.Constants.Constant.BASE_URL_GOV;

public class Gov_Client {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_GOV)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    public static <S> S createGovService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


}

