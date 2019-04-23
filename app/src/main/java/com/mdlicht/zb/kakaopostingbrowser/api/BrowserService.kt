package com.mdlicht.zb.kakaopostingbrowser.api

import com.mdlicht.zb.kakaopostingbrowser.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BrowserService {
    @GET("v2/search/blog")
    fun searchBlog(@Query("query") query: String, @Query("page") page: Int, @Query("size") size: Int, @Query("sort") sort: String = "accuracy"): Observable<Response>

    @GET("v2/search/cafe")
    fun searchCafe(@Query("query") query: String, @Query("page") page: Int, @Query("size") size: Int, @Query("sort") sort: String = "accuracy"): Observable<Response>
}