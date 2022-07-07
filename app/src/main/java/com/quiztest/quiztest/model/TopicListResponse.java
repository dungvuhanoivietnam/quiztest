package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopicListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    public class Data {
        @SerializedName("data")
        @Expose
        ArrayList<TestItem> listTopicByType;


        public ArrayList<TestItem> getListTopicByType() {
            return listTopicByType;
        }
    }

    public Data getData() {
        return data;
    }
}
