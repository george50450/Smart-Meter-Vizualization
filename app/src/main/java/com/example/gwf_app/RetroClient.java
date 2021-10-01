package com.example.gwf_app;

import com.example.gwf_app.Login.LoginService;
import com.example.gwf_app.Refresh.RefreshService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit getRetroFit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://test-api.gwf.ch/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static LoginService getLoginService() {
        LoginService loginService = getRetroFit().create(LoginService.class);

        return loginService;
    }

    public static RefreshService getRefreshService() {
        RefreshService refreshService = getRetroFit().create(RefreshService.class);

        return refreshService;
    }

}
