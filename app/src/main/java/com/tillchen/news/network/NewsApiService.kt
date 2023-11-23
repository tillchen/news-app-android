package com.tillchen.news.network

import com.tillchen.news.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("q") query: String? = null,
    ): Response<NewsResponse>
}
