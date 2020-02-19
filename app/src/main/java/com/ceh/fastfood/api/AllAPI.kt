package com.ceh.fastfood.api

import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.menu.Menu
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllAPI {
    private val apiInterface:APIInterface
    companion object{
        const val BASE_URL = "http://food-delivery.minpike.me/api/setup/"
    }
    init {
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(APIInterface::class.java)
    }
    fun getMenuResult():Call<Menu>{
        return apiInterface.getMenus()
    }
    fun getCategoryResult():Call<Category>{
        return apiInterface.getCategories()
    }
    fun getMenuByCategoryResult(category_id:String):Call<Menu>{
        return apiInterface.getMenuByCategories(category_id)
    }
    fun getMenuByRestaurantResult(restaurant_id:String):Call<Menu>{
        return apiInterface.getMenuByRestaurants(restaurant_id)
    }
}