package com.example.news.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.news.api.APIManager
import com.example.news.api.ApiConstant
import com.example.news.api.model.*
import com.example.news.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    companion object{
        fun getInstance(source: SourceItem) : NewsFragment{
        val newNewsFragment = NewsFragment()
            newNewsFragment.source = source
            return newNewsFragment
        }
    }
    lateinit var source: SourceItem
    lateinit var adapter: NewsAdapter
    lateinit var binding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater , container , false)
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            getNews()
        viewModel.getNews(source.id ?: "")
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.bindingNewsList.observe(viewLifecycleOwner,object :Observer<List<News?>?>{
            override fun onChanged(t: List<News?>?) {
                bindNewsList(t)
            }
        })
        viewModel.showLoadingLayout.observe(viewLifecycleOwner , object :Observer<Boolean>{
            override fun onChanged(t: Boolean?) {
                if (t == true)
                {
                    showLoadingLayout()
                }else{
                    binding.loadingIndecator.isVisible = false
                }
            }

        })
        viewModel.showErrorLayout.observe(viewLifecycleOwner , object :Observer<String?>{
            override fun onChanged(t: String?) {
                showErrorLayout(t)
            }

        })
    }

//    private fun getNews() {
//        showLoadingLayout()
//
//        APIManager.getAPI().getNews(ApiConstant.APIKEY , source.id ?:"").enqueue(object :Callback<NewsResponse>{
//            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
//                if(response.isSuccessful)
//                {
//                    binding.loadingIndecator.isVisible = false
//                    // recyclerView showing News
//                    bindNewsList(response.body()?.articles)
////                    return
//                }else{
//                    val gson = Gson()
//                    val error_Response = gson.fromJson(response.errorBody()?.string() , NewsResponse::class.java)
//                    Log.e("fail-to-load",error_Response.message.toString())
//                    showErrorLayout(error_Response.message)
//                }
//            }
//
//            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                showErrorLayout(t.localizedMessage?.toString())
//            }
//
//        })
//    }

    private fun bindNewsList(articles: List<News?>?) {


        adapter = NewsAdapter(articles)
        binding.newsRecycler.adapter = adapter

    }

    private fun showLoadingLayout() {
        binding.loadingIndecator.isVisible = true
        binding.errorLayout.isVisible = false
    }
    private fun showErrorLayout(errorMessage: String?) {
        binding.errorLayout.isVisible= true
        binding.loadingIndecator.isVisible = false
        binding.errorMessage.text = errorMessage

    }
}