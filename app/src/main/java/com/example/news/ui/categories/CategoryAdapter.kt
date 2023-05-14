package com.example.news.ui.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.news.databinding.ItemCategoryBinding

class CategoryAdapter(var items : List<Category>) : Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCategoryBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root){

    }

    var onItemClickListener : OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        with(holder)
        {
            with(binding)
            {
//                image.setImageResource(item.imageId)
//                title.setText(item.name)
                //data binding and binding adapter
                category = item
                executePendingBindings() // implement and refresh binding
//                container.setBackgroundColor(ContextCompat.getColor(root.context ,item.backgroundColorId ))
                onItemClickListener.let {
                    root.setOnClickListener{
                        onItemClickListener?.onIteClick(item , position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int =items.size


    interface OnItemClickListener{
        fun onIteClick(item : Category , position: Int)
    }
}