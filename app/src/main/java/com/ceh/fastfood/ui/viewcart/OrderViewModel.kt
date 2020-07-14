package com.ceh.fastfood.ui.viewcart

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceh.fastfood.api.AllAPI
import com.ceh.fastfood.model.order.OrderDetailCart
import com.ceh.fastfood.model.order.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class OrderViewModel : ViewModel() {

    var responseMessage: MutableLiveData<String> = MutableLiveData()
    var error: MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    fun getResponseMessage(): LiveData<String> = responseMessage

    private val api: AllAPI = AllAPI()

    fun postOrder(userId: Int, orderDate: LocalDate, total: String, orderDetail: String) {
        loading.value = true
        val apiCall = api.postOrder(userId, orderDate, total, orderDetail)

        apiCall.enqueue(object : Callback<OrderResponse> {

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.d("ERROR >>> ", t.toString())
                error.value = true
                loading.value = false
            }

            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                Log.d("Response >>> ", response.body().toString())
                response.isSuccessful.let {
                    loading.value = false
                    responseMessage.value = response.body()!!.message
                }
            }

        })
    }
}