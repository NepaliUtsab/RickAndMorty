package com.example.ricknmorty.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ricknmorty.helpers.ApiInterface;
import com.example.ricknmorty.helpers.MyApplication;
import com.example.ricknmorty.models.BaseResponseModel;
import com.example.ricknmorty.models.CharacterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepo {

    final String TAG = getClass().getSimpleName();

    int currentPage = 1;

    public MutableLiveData<List<CharacterModel>> getCharacterList(){

//        Mutable list to return for View Model
        final MutableLiveData<List<CharacterModel>> mutableCharacterList = new MutableLiveData<>();

        ApiInterface apiService = MyApplication.getRetrofit().create(ApiInterface.class);

//        Gets character list from the api and sets value to Mutable list in View model
        apiService.characterList(currentPage).enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    //
                    mutableCharacterList.setValue(response.body().results);
                    if(response.body().info.next != null && response.body().info.next.length() > 0){
//                        If Api has next page then manually increasing the page number since the api does
//
                        currentPage++;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {
//                For now just prints the error message in the console
//                Could be improved to notify to user more clearly
                Log.e(TAG, t.getMessage());
            }
        });
        return mutableCharacterList;
    }


}
