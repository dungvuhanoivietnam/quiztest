package com.quiztest.quiztest.retrofit;

import com.quiztest.quiztest.model.BaseResponse;
import com.quiztest.quiztest.model.HistoryResponse;
import com.quiztest.quiztest.model.UploadAvatarResponse;
import com.quiztest.quiztest.model.UserInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestAPI {
    @POST("auth/register-account")
    Call<ResponseBody> registerAccount(@Query("email") String email,
                                       @Query("name") String name,
                                       @Query("password") String password,
                                       @Query("confirm_password") String confirm_password);

    @GET("user")
    Call<UserInfoResponse> getUserInfo();

    @GET("quiz/get-history-participation")
    Call<HistoryResponse> getHistorys(@Query("page") String page);

    @PUT("user/update-profile")
    Call<BaseResponse> updateProfile(@Query("name") String name, @Query("email") String email);

    @GET("user/logout")
    Call<BaseResponse> logOut();

    @Multipart
    @POST("user/change-avatar")
    Call<UploadAvatarResponse> uploadAvatar(@Part MultipartBody.Part avatar);
}
