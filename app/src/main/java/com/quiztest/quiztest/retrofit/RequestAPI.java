package com.quiztest.quiztest.retrofit;

import com.quiztest.quiztest.model.AuthResponse;
import com.quiztest.quiztest.model.BaseResponse;
import com.quiztest.quiztest.model.HistoryResponse;
import com.quiztest.quiztest.model.HomeDataResponse;
import com.quiztest.quiztest.model.OtpDataResponse;
import com.quiztest.quiztest.model.TopicListResponse;
import com.quiztest.quiztest.model.UploadAvatarResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.model.UserRankingResponse;
import com.quiztest.quiztest.model.UserReponse;
import com.quiztest.quiztest.model.VerifyEmailResponse;

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
    Call<AuthResponse> registerAccount(@Query("email") String email,
                                       @Query("name") String name,
                                       @Query("password") String password,
                                       @Query("confirm_password") String confirm_password,
                                       @Query("language") String language);

    @GET("user")
    Call<UserInfoResponse> getUserInfo();

    @GET("user-guest")
    Call<UserInfoResponse> getGuestUserInfo();

    @POST("auth/login")
    Call<AuthResponse> loginAccount(
            @Query("email") String email,
            @Query("password") String password
    );


    @GET("quiz/get-history-participation")
    Call<HistoryResponse> getHistorys(@Query("page") String page);

    @PUT("user/update-profile")
    Call<BaseResponse> updateProfile(@Query("name") String name, @Query("email") String email);

    @GET("user/logout")
    Call<BaseResponse> logOut();

    @GET("quiz/get-data-for-home")
    Call<HomeDataResponse> getDataForHome();

    @GET("user/get-ranking/{type}")
    Call<UserRankingResponse> getRankingStarByType(@Path("type") String type);

    @GET("quiz/topic-list-by-type/{type}")
    Call<TopicListResponse> getTopicByType(@Path("type") int type);

    @Multipart
    @POST("user/change-avatar")
    Call<UploadAvatarResponse> uploadAvatar(@Part MultipartBody.Part avatar);

    @PUT("user/change-password")
    Call<BaseResponse> changePassword(@Query("current_password") String current_password, @Query("password") String password, @Query("confirm_password") String confirm_password);

    @POST("auth/request-set-password")
    Call<VerifyEmailResponse> verifyMail(@Query("email") String email);

    @POST("auth/login-social")
    Call<AuthResponse> loginSocial(@Query("provider_name") String provideName,
                                   @Query("access_token") String accessToken,
                                   @Query("device_id") String deviceId,
                                   @Query("language") String language);

    @POST("auth/resend-otp")
    Call<OtpDataResponse> resendOtp(@Query("email") String email, @Query("verify_style") String verifyStyle, @Query("verify_type") String verifyType);

    @POST("auth/verify-otp")
    Call<AuthResponse> verifyOtp(@Query("verify_type") String verifyType, @Query("email")
            String email, @Query("otp") String otp);

}
