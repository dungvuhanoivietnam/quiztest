package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.retrofit.RequestAPI;
import com.quiztest.quiztest.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends BaseFragment {
    protected RequestAPI requestAPI;
    public static final String TAG = HomeFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = RetrofitClient.getInstance();
        requestAPI = retrofit.create(RequestAPI.class);

        Call<ResponseBody> call =
                requestAPI.registerAccount(
                "luyenphong00@gmail.ccom",
                "test",
                "12345678",
                "12345678");
        call.enqueue(new callBack());
    }

    private class callBack implements Callback<ResponseBody> {

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            //ok
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            // faild
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

}
