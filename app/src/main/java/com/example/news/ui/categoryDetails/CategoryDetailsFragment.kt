package com.example.news.ui.categoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.api.APIManager
import com.example.news.api.ApiConstant
import com.example.news.api.model.SourceItem
import com.example.news.api.model.SourcesResponse
import com.example.news.databinding.FragmentDetailsCategoryBinding
import com.example.news.ui.categories.Category
import com.example.news.ui.categories.CategoryFragment
import com.example.news.ui.news.NewsFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsFragment : Fragment() {

    companion object{
        fun getInstance(category: Category):CategoryDetailsFragment{
            var categoryFragment = CategoryDetailsFragment()
            categoryFragment.category = category
            return categoryFragment
        }
    }
    lateinit var category:Category
    lateinit var binding: FragmentDetailsCategoryBinding
    lateinit var viewModel: CategoryDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //loadNewsSources()
        viewModel.loadNewsSources(category.id)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner){
            bindSourcesInTabLayout(it)
        }
        viewModel.showLoadingLayout.observe(viewLifecycleOwner, object :Observer<Boolean>{
            override fun onChanged(t: Boolean?) {
                // t changed by viewModel
                if(t == true)
                {
                    binding.loadingIndecator.isVisible = true
                    binding.errorLayout.isVisible = false
                }else{
                    binding.loadingIndecator.isVisible = false
                }
            }

        })
        viewModel.showErrorLayout.observe(viewLifecycleOwner) {
            showErrorLayout(it)
        }
    }

    fun changeNewsFragment(source : SourceItem)
    {
        childFragmentManager.beginTransaction().replace(R.id.fragment_container , NewsFragment.getInstance(source)).commit()
    }
//---------------------------------MVVM-----------------------------------------
//    private fun loadNewsSources() {
//        binding.loadingIndecator.isVisible = true
//        binding.errorLayout.isVisible = false
//        APIManager.getAPI().getSources(ApiConstant.APIKEY , category.id).enqueue(object : Callback<SourcesResponse> {
//            override fun onResponse(
//                call: Call<SourcesResponse>,
//                response: Response<SourcesResponse>
//            ) {
//                binding.loadingIndecator.isVisible = false
//
//                if(response.isSuccessful) {
//                    bindSourcesInTabLayout(response.body()?.sources)
//                }else{
//                    val gson = Gson()
//                    val error_Response = gson.fromJson(response.errorBody()?.string() , SourcesResponse::class.java)
//                    showErrorLayout(error_Response.message)
//                }
//            }
//
//            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
//
//                binding.loadingIndecator.isVisible = false
//                showErrorLayout(t.localizedMessage?.toString())
//            }
//
//        })
//    }

    private fun showErrorLayout(errorMessage: String?) {
        binding.errorLayout.isVisible= true
        binding.loadingIndecator.isVisible = false
        binding.errorMessage.text = errorMessage

    }

    fun bindSourcesInTabLayout(sourcesList : List<SourceItem?>?)
    {
        sourcesList?.forEach {
            val tab  = binding.tablayout.newTab()
            tab.text = it?.name
            tab.tag = it
            binding.tablayout.addTab(tab)
        }
        binding.tablayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = tab?.tag as SourceItem
                changeNewsFragment(source)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as SourceItem
                changeNewsFragment(source)
            }
        })
        binding.tablayout.getTabAt(0)?.select()
    }
}