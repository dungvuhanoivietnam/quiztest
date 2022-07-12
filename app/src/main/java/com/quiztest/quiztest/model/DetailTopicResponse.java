package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailTopicResponse extends BaseResponse{
    @SerializedName("data")
    @Expose
    private Data data;

    public class Data {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("time_to_do")
        @Expose
        private Integer timeToDo;
        @SerializedName("allotment")
        @Expose
        private String allotment;
        @SerializedName("money_bonus")
        @Expose
        private Integer moneyBonus;
        @SerializedName("star_bonus")
        @Expose
        private Integer starBonus;
        @SerializedName("fee_star")
        @Expose
        private Integer feeStar;
        @SerializedName("total_question_count")
        @Expose
        private Integer totalQuestionCount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getTimeToDo() {
            return timeToDo;
        }

        public void setTimeToDo(Integer timeToDo) {
            this.timeToDo = timeToDo;
        }

        public String getAllotment() {
            return allotment;
        }

        public void setAllotment(String allotment) {
            this.allotment = allotment;
        }

        public Integer getMoneyBonus() {
            return moneyBonus;
        }

        public void setMoneyBonus(Integer moneyBonus) {
            this.moneyBonus = moneyBonus;
        }

        public Integer getStarBonus() {
            return starBonus;
        }

        public void setStarBonus(Integer starBonus) {
            this.starBonus = starBonus;
        }

        public Integer getFeeStar() {
            return feeStar;
        }

        public void setFeeStar(Integer feeStar) {
            this.feeStar = feeStar;
        }

        public Integer getTotalQuestionCount() {
            return totalQuestionCount;
        }

        public void setTotalQuestionCount(Integer totalQuestionCount) {
            this.totalQuestionCount = totalQuestionCount;
        }

    }
}
