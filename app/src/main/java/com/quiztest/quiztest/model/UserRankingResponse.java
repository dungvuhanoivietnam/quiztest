package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserRankingResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<UserRanking> data;

    public ArrayList<UserRanking> getData() {
        return data;
    }

    public void setData(ArrayList<UserRanking> data) {
        this.data = data;
    }

    public class UserRanking {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("avatar")
        @Expose
        private String avatar;

        @SerializedName("total_bonus")
        @Expose
        private int total_bonus;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTotal_bonus() {
            return total_bonus;
        }

        public void setTotal_bonus(int total_bonus) {
            this.total_bonus = total_bonus;
        }
    }
}
