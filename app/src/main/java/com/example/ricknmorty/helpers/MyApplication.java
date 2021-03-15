package com.example.ricknmorty.helpers;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static MyApplication appInstance;
//  Globally initializing retrofit
    private static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient;
    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static MyApplication getAppInstance() {
        return appInstance;
    }

//    This function checks if internet is available
//    Returns a boolean
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo!= null && networkInfo.isConnected();

    }

//    Returns Retrofit instance
    public static Retrofit getRetrofit(){
        if (okHttpClient == null)
            initOkHttp();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);
        okHttpClient = httpClient.build();
    }
}
