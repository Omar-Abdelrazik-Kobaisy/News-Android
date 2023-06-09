package com.example.news.ui.categoryDetails

import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.api.APIManager
import com.example.news.api.ApiConstant
import com.example.news.api.model.SourceItem
import com.example.news.api.model.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsViewModel : ViewModel() {


    val sourcesLiveData = MutableLiveData<List<SourceItem?>?>()
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String?>()

     fun loadNewsSources(categoryId:String) {
//        binding.loadingIndecator.isVisible = true
//        binding.errorLayout.isVisible = false

         showLoadingLayout.value = true

        APIManager.getAPI().getSources(ApiConstant.APIKEY , categoryId).enqueue(object :
            Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                //binding.loadingIndecator.isVisible = false

                showLoadingLayout.value = false

                if(response.isSuccessful) {
                    //bindSourcesInTabLayout(response.body()?.sources)
                    // we have list of sources <<<-----------------
                    sourcesLiveData.value = response.body()?.sources
                }else{
                    val gson = Gson()
                    val error_Response = gson.fromJson(response.errorBody()?.string() , SourcesResponse::class.java)
                    //showErrorLayout(error_Response.message)
                    showErrorLayout.value = error_Response.message ?: ""

                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {

//                binding.loadingIndecator.isVisible = false
//                showErrorLayout(t.localizedMessage?.toString())

                showLoadingLayout.value = false
                showErrorLayout.value = t.localizedMessage ?: ""
            }

        })
    }
}