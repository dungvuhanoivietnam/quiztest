package com.quiztest.quiztest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestItem implements Parcelable {
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

    protected TestItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        moneyBonus = in.readInt();
        starBonus = in.readInt();
        feeStar = in.readInt();
        totalQuestionCount = in.readString();
    }

    public static final Creator<TestItem> CREATOR = new Creator<TestItem>() {
        @Override
        public TestItem createFromParcel(Parcel in) {
            return new TestItem(in);
        }

        @Override
        public TestItem[] newArray(int size) {
            return new TestItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeInt(moneyBonus);
        parcel.writeInt(starBonus);
        parcel.writeInt(feeStar);
        parcel.writeString(totalQuestionCount);
    }
}
