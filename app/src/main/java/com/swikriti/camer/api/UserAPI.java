package com.swikriti.camer.api;

import com.swikriti.camer.model.User;
import com.swikriti.camer.serverresponse.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {


    @POST("users/signup")
    Call< SignUpResponse> registerUser(@Body User users);

}


