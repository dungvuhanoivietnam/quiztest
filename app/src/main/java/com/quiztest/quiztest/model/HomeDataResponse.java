package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeDataResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public class Data {
        @SerializedName("topics_hot")
        @Expose
        ArrayList<TestItem> listEarningTasks;

        @SerializedName("topics_recommend")
        @Expose
        ArrayList<TestItem> listGetMoreStars;

        public ArrayList<TestItem> getListEarningTasks() {
            return listEarningTasks;
        }

        public ArrayList<TestItem> getListGetMoreStars() {
            return listGetMoreStars;
        }
    }

    public Data getData() {
        return data;
    }
}
