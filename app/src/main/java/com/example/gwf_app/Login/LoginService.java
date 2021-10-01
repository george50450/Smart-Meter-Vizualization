package com.example.gwf_app.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("auth/token/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

}
