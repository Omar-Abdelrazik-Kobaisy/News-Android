package com.example.news.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.api.APIManager
import com.example.news.api.model.SourcesResponse
import com.example.news.databinding.ActivityMainBinding
import com.example.news.ui.categories.Category
import com.example.news.ui.categories.CategoryFragment
import com.example.news.ui.categoryDetails.CategoryDetailsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()  {
    lateinit var binding: ActivityMainBinding
    //val APIKEY : String = "28aa763afec34d1480aa39cb75b1ddfc"
    val categoriesFragment = CategoryFragment()

//    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this , R.layout.activity_main )
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        categoriesFragment.apply {
            onCategoryClickListener = object : CategoryFragment.OnCategoryClickListener{
                override fun onCategoryClick(category: Category) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container , CategoryDetailsFragment.getInstance(category))
                        .addToBackStack(null).commit()
                }

            }
        }

//        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawer,binding.toolBar , 0 , 0)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.categories -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container , categoriesFragment).commit()
                R.id.settings -> Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
            }
            binding.drawer.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        supportFragmentManager.beginTransaction().replace(R.id.fragment_container , categoriesFragment).commit()



//        APIManager.getAPI().getSources(APIKEY ).enqueue(object :Callback<SourcesResponse>{
//            override fun onResponse(
//                call: Call<SourcesResponse>,
//                response: Response<SourcesResponse>
//            ) {
//                Log.e("--------->response Code",response.code().toString())
//                Log.e("response is Successfull", response.isSuccessful.toString())
//                Log.e("response body", response.body().toString())
//                Log.e("response error body",response.errorBody()?.string()?:"")
//                Log.w("omar",response.code().toString())
//            }
//
//            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
//                Log.e("onfail" , t.localizedMessage?.toString()?:"")
//            }
//
//        })
    }


}