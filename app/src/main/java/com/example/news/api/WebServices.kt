package com.example.news.api

import com.example.news.api.model.NewsResponse
import com.example.news.api.model.Source
import com.example.news.api.model.SourceItem
import com.example.news.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    //https://newsapi.org/v2/top-headlines/sources
    @GET("v2/top-headlines/sources?")
    fun getSources(@Query("apiKey") apiKey:String , @Query("category") category : String):Call<SourcesResponse>
    @GET("v2/everything")
    fun getNews(@Query("apikey") apiKey:String , @Query("sources")source: String):Call<NewsResponse>
}