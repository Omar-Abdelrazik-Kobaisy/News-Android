package com.example.news.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.api.model.News
import com.example.news.databinding.ItemNewsBinding

class NewsAdapter(var news : List<News?>?) :Adapter<NewsAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemNewsBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = news?.get(position)
        with(holder)
        {
            with(binding){
//                newsTitle.text = item?.title
//                newsAuther.text = item?.author
//                newsTime.text = item?.publishedAt
                //data binding and binding adapter
                news = item
                executePendingBindings()
            }
        }
//        Glide.with(holder.itemView).load(item?.urlToImage).centerCrop().placeholder(R.drawable.ic_broken_image)
//            .into(holder.binding.newsImg)



        //here data binding and binding adapter helps me to make logic inside xml and  calling function and treat with it like XML attribute
        //we simplify code above in to two lines
    //                                          --------->   holder.binding.news = news[position]   <------------------------------
        //                                      --------->   executePendingBindings()               <------------------------------ thanks to data binding and binding adapter
    }

    override fun getItemCount(): Int =news?.size ?:0
}