package com.ceh.fastfood.api

import android.util.Log
import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.menu.Menu
import com.ceh.fastfood.model.order.OrderDetailCart
import com.ceh.fastfood.model.order.OrderResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class AllAPI {
    private val apiInterface:APIInterface
    companion object{
        const val BASE_URL = "http://food-delivery-api.chaneihmwe.com/api/setup/"
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
    fun getMenuByCategoryResult(category_id:Int):Call<Menu>{
        return apiInterface.getMenuByCategories(category_id)
    }
    fun getMenuByRestaurantResult(restaurant_id:Int):Call<Menu>{
        return apiInterface.getMenuByRestaurants(restaurant_id)
    }
    fun postOrder(userId: Int, orderDate: LocalDate, total:String, orderDetail: String): Call<OrderResponse> {
        Log.d("ApiDetial", orderDetail);
        return apiInterface.postOrder(userId, orderDate, total, orderDetail)
    }
}