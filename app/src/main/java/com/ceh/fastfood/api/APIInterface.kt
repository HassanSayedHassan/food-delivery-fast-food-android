package com.ceh.fastfood.api

import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.menu.Menu
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {
    @GET("menu")
    fun getMenus():Call<Menu>

    @GET("category")
    fun getCategories():Call<Category>

    @GET("menu_by_category")
    fun getMenuByCategories(@Query("category_id") category_id:String):Call<Menu>

    @GET("menus_by_restaurant")
    fun getMenuByRestaurants(@Query("restaurant_id") restaurant_id:String):Call<Menu>

}