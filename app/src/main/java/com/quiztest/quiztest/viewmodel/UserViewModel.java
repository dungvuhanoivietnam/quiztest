package com.quiztest.quiztest.viewmodel;

import androidx.core.util.Consumer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.quiztest.quiztest.model.BaseResponse;
import com.quiztest.quiztest.model.ChangePassResponse;
import com.quiztest.quiztest.model.HistoryResponse;
import com.quiztest.quiztest.model.HomeDataResponse;
import com.quiztest.quiztest.model.TopicListResponse;
import com.quiztest.quiztest.model.UploadAvatarResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.model.UserRankingResponse;
import com.quiztest.quiztest.retrofit.RequestAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private final SavedStateHandle state;

    public UserViewModel(SavedStateHandle state) {
        this.state = state;
    }

    private UserInfoResponse.UserInfo userInfo = new UserInfoResponse.UserInfo();
    private ArrayList<HistoryResponse.History> histories = new ArrayList<>();
    private int pageHistory = 0;

    public void clearViewModel() {
        userInfo = new UserInfoResponse.UserInfo();
        histories.clear();
    }

    public void getUserInfo(RequestAPI requestAPI, Consumer consumer) {
        requestAPI.getUserInfo().enqueue(new callBack(consumer));
    }

    public void getGuestUserInfo(RequestAPI requestAPI, Consumer consumer) {
        requestAPI.getGuestUserInfo().enqueue(new callBack(consumer));
    }

    public void getHistory(RequestAPI requestAPI, int page, Consumer consumer) {
        pageHistory = page;
        requestAPI.getHistorys(page + "").enqueue(new callBackHistory(consumer));
    }

    public UserInfoResponse.UserInfo getUserInfoResponse() {
        return userInfo;
    }

    private class callBack implements Callback<UserInfoResponse> {

        private Consumer consumer;

        public callBack(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
            UserInfoResponse userInfoResponse = response.body();
            if (userInfoResponse != null) {
                userInfo = userInfoResponse.getData().getUserInfo();
                consumer.accept(userInfoResponse);
            }

        }

        @Override
        public void onFailure(Call<UserInfoResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    private class callBackGetDataHome implements Callback<HomeDataResponse> {

        private Consumer consumer;

        public callBackGetDataHome(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<HomeDataResponse> call, Response<HomeDataResponse> response) {
            HomeDataResponse homeDataResponse = response.body();
            if (homeDataResponse != null) {
                consumer.accept(homeDataResponse);
            }else {
                consumer.accept(response.code());
            }
        }

        @Override
        public void onFailure(Call<HomeDataResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    private class callBackGetRankingByType implements Callback<UserRankingResponse> {

        private Consumer consumer;

        public callBackGetRankingByType(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<UserRankingResponse> call, Response<UserRankingResponse> response) {
            UserRankingResponse userRankingResponse = response.body();
            if (userRankingResponse != null) {
                consumer.accept(userRankingResponse);
            } else {
                consumer.accept(response.code());
            }
        }

        @Override
        public void onFailure(Call<UserRankingResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    private class callBackGetTopicByType implements Callback<TopicListResponse> {

        private Consumer consumer;

        public callBackGetTopicByType(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<TopicListResponse> call, Response<TopicListResponse> response) {
            TopicListResponse topicListResponse = response.body();
            consumer.accept(topicListResponse);
        }

        @Override
        public void onFailure(Call<TopicListResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    private class callBackHistory implements Callback<HistoryResponse> {

        private Consumer consumer;

        public callBackHistory(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
            HistoryResponse userInfoResponse = response.body();
            if (userInfoResponse != null) {
                histories.addAll(userInfoResponse.getData().getData());
            }
            consumer.accept(new Object());
        }

        @Override
        public void onFailure(Call<HistoryResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    public ArrayList<HistoryResponse.History> getHistories() {
        return histories;
    }

    public int getPageHistory() {
        return pageHistory;
    }

    public void updateProfile(RequestAPI requestAPI, String name, String email, Consumer consumer) {
        requestAPI.updateProfile(name, email).enqueue(new callBackUpdateProfile(consumer));
    }

    private static class callBackUpdateProfile implements Callback<BaseResponse> {

        private Consumer consumer;

        public callBackUpdateProfile(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
            BaseResponse userInfoResponse = response.body();
            consumer.accept(userInfoResponse);
        }

        @Override
        public void onFailure(Call<BaseResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    public void logout(RequestAPI requestAPI, Consumer consumer) {
        requestAPI.logOut().enqueue(new callBackLogout(consumer));
    }

    public void getDataForHome(RequestAPI requestAPI, Consumer consumer){
        requestAPI.getDataForHome().enqueue(new callBackGetDataHome(consumer));
    }

    public void getTopicListByType(RequestAPI requestAPI, int type, Consumer consumer){
        requestAPI.getTopicByType(type).enqueue(new callBackGetTopicByType(consumer));
    }

    public void getUserRankingByType(RequestAPI requestAPI, String type, Consumer consumer){
        requestAPI.getRankingStarByType(type).enqueue(new callBackGetRankingByType(consumer));
    }

    private static class callBackLogout implements Callback<BaseResponse> {

        private Consumer consumer;

        public callBackLogout(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
            BaseResponse userInfoResponse = response.body();
            if (userInfoResponse == null)
            {
                try {
                    userInfoResponse = new Gson().fromJson(response.errorBody().string(),BaseResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            consumer.accept(userInfoResponse);
        }

        @Override
        public void onFailure(Call<BaseResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    private static class callBackUploadAvatar implements Callback<UploadAvatarResponse> {

        private Consumer consumer;

        public callBackUploadAvatar(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<UploadAvatarResponse> call, Response<UploadAvatarResponse> response) {
            UploadAvatarResponse userInfoResponse = response.body();
            consumer.accept(userInfoResponse);
        }

        @Override
        public void onFailure(Call<UploadAvatarResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }

    public void uploadAvatar(RequestAPI requestAPI, String fileName, Consumer consumer) {
        File file = new File(fileName);
        if (!file.exists())
            return;
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        requestAPI.uploadAvatar(body).enqueue(new callBackUploadAvatar(consumer));
    }

    public void changePass(RequestAPI requestAPI, String currentPass, String pass, String rePass, Consumer consumer) {
        requestAPI.changePassword(currentPass, pass, rePass).enqueue(new callBackLogout(consumer));
    }

    private static class callBackChangePass implements Callback<ChangePassResponse> {

        private Consumer consumer;

        public callBackChangePass(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
            ChangePassResponse userInfoResponse = response.body();
            if (userInfoResponse == null)
            {
                try {
                    consumer.accept(new Gson().fromJson(response.errorBody().string(),BaseResponse.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            consumer.accept(userInfoResponse);
        }

        @Override
        public void onFailure(Call<ChangePassResponse> call, Throwable t) {
            consumer.accept(t);
        }
    }
}
