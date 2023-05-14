package com.example.news

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageWithUrl")
fun loadImageFromUrl(imageView : ImageView , url:String)
{
    Glide.with(imageView).load(url).centerCrop().placeholder(R.drawable.ic_broken_image)
        .into(imageView)
}

@BindingAdapter("changeBGColor")
fun changeCardBackGroundColor(cardView: CardView , colorId : Int)
{
//    cardView.setBackgroundColor(ContextCompat.getColor(cardView.context ,colorId))
    cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context ,colorId))

}

@BindingAdapter("changeImage")
fun changeImage(imageView: ImageView , imageId : Int)
{
    imageView.setImageResource(imageId)

}