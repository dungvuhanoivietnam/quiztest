package com.quiztest.quiztest.viewmodel;

import androidx.core.util.Consumer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.quiztest.quiztest.model.BaseResponse;
import com.quiztest.quiztest.model.HistoryResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.retrofit.RequestAPI;

import java.util.ArrayList;

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
                userInfo = userInfoResponse.getData();
            }
            consumer.accept(new Object());
        }

        @Override
        public void onFailure(Call<UserInfoResponse> call, Throwable t) {
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

    private class callBackUpdateProfile implements Callback<BaseResponse> {

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

    private class callBackLogout implements Callback<BaseResponse> {

        private Consumer consumer;

        public callBackLogout(Consumer consumer) {
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
}
