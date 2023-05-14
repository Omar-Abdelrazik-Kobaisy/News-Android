package com.example.news.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIManager {

    //singletone pattern
    companion object{
        var retrofit:Retrofit? = null
       @Synchronized
       private fun getInstance():Retrofit
        {
            if (retrofit == null){
                val loggingInterceptor = HttpLoggingInterceptor {
                    Log.e("aPiI", it)
                }

                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY



                val okHttpClient =  OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun getAPI(): WebServices
        {
            return getInstance().create(WebServices::class.java)
        }
    }

}