package com.example.ricknmorty.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponseModel {
    @SerializedName("info")
    @Expose
    public Info info;
    @SerializedName("results")
    @Expose
    public List<CharacterModel> results = null;

    public class Info {

        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("pages")
        @Expose
        public Integer pages;
        @SerializedName("next")
        @Expose
        public String next;
        @SerializedName("prev")
        @Expose
        public String prev;
    }

}



