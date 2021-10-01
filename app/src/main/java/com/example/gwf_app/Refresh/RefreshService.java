package com.example.gwf_app.Refresh;

import com.example.gwf_app.Refresh.RefreshRequest;
import com.example.gwf_app.Refresh.RefreshResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RefreshService {
    @POST("auth/token/refresh/")
    Call<RefreshResponse> userRefresh(@Body RefreshRequest refreshRequest);
}
