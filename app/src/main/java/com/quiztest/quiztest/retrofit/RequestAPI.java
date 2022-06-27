package com.quiztest.quiztest.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestAPI {
    @POST("register-account")
    Call<ResponseBody> registerAccount
            (@Query("email") String email,
             @Query("name") String name,
             @Query("password") String password,
             @Query("confirm_password") String confirm_password);


}
