package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestItem {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("money_bonus")
    @Expose
    private int moneyBonus;

    @SerializedName("star_bonus")
    @Expose
    private int starBonus;

    @SerializedName("fee_star")
    @Expose
    private int feeStar;

    @SerializedName("total_question_count")
    @Expose
    private String totalQuestionCount;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getMoneyBonus() {
        return moneyBonus;
    }

    public int getStarBonus() {
        return starBonus;
    }

    public int getFeeStar() {
        return feeStar;
    }

    public String getTotalQuestionCount() {
        return totalQuestionCount;
    }
}
