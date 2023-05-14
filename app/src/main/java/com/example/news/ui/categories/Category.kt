package com.example.news.ui.categories

import com.example.news.R

data class Category(
    val id : String,
    val name: String,
    val imageId : Int,
    val backgroundColorId :Int
){
    companion object{
        fun getListOfCategory() :List<Category>{
//business
// entertainment
// general
// health
// science
// sports
// technology
            return listOf(
                Category(id = "sports", name = "Sports", imageId = R.drawable.ball, backgroundColorId = R.color.Red),
                Category(id = "general", name = "Poltics", imageId = R.drawable.politics, backgroundColorId = R.color.blue),
                Category(id = "health", name = "Health", imageId = R.drawable.health, backgroundColorId = R.color.pinck),
                Category(id = "business", name = "Business", imageId = R.drawable.bussines, backgroundColorId = R.color.brwon),
                Category(id = "entertainment", name = "Envirinment", imageId = R.drawable.environment, backgroundColorId = R.color.babyblue),
                Category(id = "science", name = "Science", imageId = R.drawable.science, backgroundColorId = R.color.yellow)
            )

        }
    }
}
