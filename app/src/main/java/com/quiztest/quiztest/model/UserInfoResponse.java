package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private UserInfo data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class UserInfo {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("total_money")
        @Expose
        private Integer totalMoney;
        @SerializedName("total_star")
        @Expose
        private Integer totalStar;
        @SerializedName("google_provider_id")
        @Expose
        private Object googleProviderId;
        @SerializedName("facebook_provider_id")
        @Expose
        private Object facebookProviderId;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(Integer totalMoney) {
            this.totalMoney = totalMoney;
        }

        public Integer getTotalStar() {
            return totalStar;
        }

        public void setTotalStar(Integer totalStar) {
            this.totalStar = totalStar;
        }

        public Object getGoogleProviderId() {
            return googleProviderId;
        }

        public void setGoogleProviderId(Object googleProviderId) {
            this.googleProviderId = googleProviderId;
        }

        public Object getFacebookProviderId() {
            return facebookProviderId;
        }

        public void setFacebookProviderId(Object facebookProviderId) {
            this.facebookProviderId = facebookProviderId;
        }

    }
}
