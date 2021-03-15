package com.example.ricknmorty.helpers;

import com.example.ricknmorty.models.BaseResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("character/")
    Call<BaseResponseModel> characterList(@Query("page") int page);
}
