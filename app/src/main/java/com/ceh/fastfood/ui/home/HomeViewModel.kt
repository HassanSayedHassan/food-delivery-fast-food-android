package com.ceh.fastfood.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceh.fastfood.api.AllAPI
import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.menu.Menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {

    private val allAPI:AllAPI = AllAPI()

    //Menu
    var menuResults:MutableLiveData<Menu> = MutableLiveData()
    var menusLoadError:MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getResults(): LiveData<Menu> = menuResults
    fun  getError(): LiveData<Boolean> = menusLoadError
    fun getLoading(): LiveData<Boolean> = loading

    //Category
    var categoryResults:MutableLiveData<Category> = MutableLiveData()
    var categoryLoadError:MutableLiveData<Boolean> = MutableLiveData()
    var categoryloading: MutableLiveData<Boolean> = MutableLiveData()

    fun getCategoryResults(): LiveData<Category> = categoryResults
    fun getCategoryError(): LiveData<Boolean> = categoryLoadError
    fun getCategoryLoading(): LiveData<Boolean> = categoryloading

    fun loadResults(){
        loading.value = true
        val call = allAPI.getMenuResult()
        call.enqueue(object :Callback<Menu>{
            override fun onFailure(call: Call<Menu>, t: Throwable) {
                loading.value = false
                menusLoadError.value = true
            }

            override fun onResponse(call: Call<Menu>, response: Response<Menu>) {
                response.isSuccessful.let {
                    loading.value = false
                    var resultList = Menu(
                        response?.body()?.menus?: emptyList())
                    /*Log.d("Menu List", resultList.toString())*/
                    menuResults.value = resultList
                }
            }

        })
    }
    fun loadCategoryResults(){
        categoryloading.value = true
        val call = allAPI.getCategoryResult()
        call.enqueue(object :Callback<Category>{
            override fun onFailure(call: Call<Category>, t: Throwable) {
                loading.value = false
                menusLoadError.value = true
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