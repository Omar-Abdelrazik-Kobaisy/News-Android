package com.example.news.ui.news

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.api.APIManager
import com.example.news.api.ApiConstant
import com.example.news.api.model.News
import com.example.news.api.model.NewsResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val bindingNewsList = MutableLiveData<List<News?>?>()
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String?>()

     fun getNews(sourceId : String) {
        //showLoadingLayout()

         showLoadingLayout.value = true
        APIManager.getAPI().getNews(ApiConstant.APIKEY , sourceId).enqueue(object :
            Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful)
                {
                    //binding.loadingIndecator.isVisible = false
                    showLoadingLayout.value = false
                    // recyclerView showing News
                    //bindNewsList(response.body()?.articles)
                    bindingNewsList.value = response.body()?.articles
//                    return
                }else{
                    val gson = Gson()
                    val error_Response = gson.fromJson(response.errorBody()?.string() , NewsResponse::class.java)
                    Log.e("fail-to-load",error_Response.message.toString())
                    //showErrorLayout(error_Response.message)
                    showErrorLayout.value = error_Response.message
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                //showErrorLayout(t.localizedMessage?.toString())
                showErrorLayout.value = t.localizedMessage?.toString()
            }

        })
    }
}