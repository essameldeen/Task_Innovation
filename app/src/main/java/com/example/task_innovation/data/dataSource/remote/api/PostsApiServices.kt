package com.example.task_innovation.data.dataSource.remote.api


import com.example.task_innovation.BuildConfig
import com.example.task_innovation.data.dataSource.remote.response.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Query


 interface PostsApiServices {
    @GET("everything")
    suspend fun getPosts(
        @Query("q") category: String?,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): PostsResponse

}