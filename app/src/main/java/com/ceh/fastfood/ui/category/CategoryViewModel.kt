package com.ceh.fastfood.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceh.fastfood.api.AllAPI
import com.ceh.fastfood.model.category.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    private val allAPI: AllAPI = AllAPI()

    var categoryResults:MutableLiveData<Category> = MutableLiveData()
    var categoryLoadError:MutableLiveData<Boolean> = MutableLiveData()
    var categoryloading: MutableLiveData<Boolean> = MutableLiveData()

    fun getCategoryResults(): LiveData<Category> = categoryResults
    fun getCategoryError(): LiveData<Boolean> = categoryLoadError
    fun getCategoryLoading(): LiveData<Boolean> = categoryloading

    fun loadCategoryResults(){
        categoryloading.value = true
        val call = allAPI.getCategoryResult()
        call.enqueue(object : Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {
                categoryloading.value = false
                categoryLoadError.value = true
                /*Log.d("CategoryError", t.toString())*/
            }

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                response.isSuccessful.let {
                    categoryloading.value = false
                    var resultList = Category(
                        response?.body()?.categories?: emptyList())
                    /*Log.d("ListCategory", resultList.toString())*/
                    categoryResults.value = resultList
                }
            }

        })
    }
}