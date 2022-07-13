package com.quiztest.quiztest.retrofit;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.quiztest.quiztest.App;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
public class RetrofitClient {

    private static Retrofit ourInstance;

    public static void setOurInstance(Retrofit ourInstance) {
        RetrofitClient.ourInstance = ourInstance;
    }

    public static OkHttpClient okHttpClient(Context context, long time) {
        String auth = SharePrefrenceUtils.getInstance(context).getAuth();
        Interceptor interceptor;
        if (auth != null && !"".contains(auth)) {
            interceptor = new AuthenticationInterceptor(auth);
        } else {
            interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(time, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(time, TimeUnit.SECONDS)
                .writeTimeout(time, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("key-app","quiztest")
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
        return okHttpClient;
    }

    public static Retrofit getInstance() {
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .client(okHttpClient(App.getInstance(), 30))
                    .baseUrl("https://quiz-test-online.merryblue.llc/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return ourInstance;
    }

    private RetrofitClient() {
    }

}
