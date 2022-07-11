package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpDataResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Data> data;

    public class Data { }

}
