package com.mdlicht.zb.kakaopostingbrowser.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    fun create(): BrowserService {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object: Interceptor {
            @Override
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request();

                val request = original.newBuilder()
                    .header("Authorization", "KakaoAK 9d4a282de9da34e15ff1c3bb2424ed32")
                    .method(original.method(), original.body())
                    .build()

                return chain.proceed(request);
            }
        })

        val client = httpClient.build()
        return Retrofit.Builder().baseUrl("https://dapi.kakao.com/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BrowserService::class.java)
    }
}