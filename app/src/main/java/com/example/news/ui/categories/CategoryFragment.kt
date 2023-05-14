package com.example.news.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.FragmentCategoryBinding
import com.example.news.ui.categoryDetails.CategoryDetailsFragment

class CategoryFragment:Fragment() {


    lateinit var binding: FragmentCategoryBinding
    lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryAdapter(Category.getListOfCategory())
        binding.categoryRecycler.adapter = adapter
        adapter.onItemClickListener = object :CategoryAdapter.OnItemClickListener{
            override fun onIteClick(item: Category, position: Int) {
                onCategoryClickListener?.onCategoryClick(item)

                Log.e("testFragment", "clicked$position")
            }

        }
    }

    var onCategoryClickListener : OnCategoryClickListener? = null
    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }
}