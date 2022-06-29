package com.quiztest.quiztest.viewmodel;

import androidx.core.util.Consumer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.retrofit.RequestAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private final SavedStateHandle state;

    public UserViewModel(SavedStateHandle state) {
        this.state = state;
    }

    private UserInfoResponse.UserInfo userInfo = new UserInfoResponse.UserInfo();


    public void getUserInfo(RequestAPI requestAPI, Consumer consumer) {
        requestAPI.getUserInfo().enqueue(new callBack(consumer));
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

}
