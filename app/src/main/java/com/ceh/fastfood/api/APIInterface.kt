package com.ceh.fastfood.api

import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.menu.Menu
import com.ceh.fastfood.model.order.OrderDetailCart
import com.ceh.fastfood.model.order.OrderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


interface APIInterface {
    @GET("menu")
    fun getMenus():Call<Menu>

    @GET("category")
    fun getCategories():Call<Category>

    @GET("menu_by_category")
    fun getMenuByCategories(@Query("category_id") category_id:Int):Call<Menu>

    @GET("menus_by_restaurant")
    fun getMenuByRestaurants(@Query("restaurant_id") restaurant_id:Int):Call<Menu>

    @POST("order")
    fun postOrder(
        @Query("user_detail_id") userId: Int,
        @Query("order_date") orderDate: LocalDate,
        @Query("total") total: String,
        @Query("order_details") orderDetail : String
    ): Call<OrderResponse>
}