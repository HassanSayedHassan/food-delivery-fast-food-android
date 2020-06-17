package com.ceh.fastfood.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceh.fastfood.api.AllAPI
import com.ceh.fastfood.model.menu.Menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel : ViewModel() {
    private val allAPI: AllAPI = AllAPI()

    //Menu
    var menuResults:MutableLiveData<Menu> = MutableLiveData()
    var menusLoadError:MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    var categoryID: MutableLiveData<Int> = MutableLiveData()

    fun getResults(): LiveData<Menu> = menuResults
    fun  getError(): LiveData<Boolean> = menusLoadError
    fun getLoading(): LiveData<Boolean> = loading

    fun getCategoryID(): LiveData<Int> = categoryID

    fun loadResults(category_id:Int){
        categoryID.value = category_id
        //Log.d("CategoryID", categoryID.value)
        loading.value = true
        val call = allAPI.getMenuByCategoryResult(category_id)
        call.enqueue(object : Callback<Menu> {
            override fun onFailure(call: Call<Menu>, t: Throwable) {
                loading.value = false
                menusLoadError.value = true
            }

            override fun onResponse(call: Call<Menu>, response: Response<Menu>) {
                response.isSuccessful.let {
                    loading.value = false
                    var resultList = Menu(
                        response?.body()?.menus?: emptyList())
                    //Log.d("MenuByCategoryList", resultList.toString())
                    menuResults.value = resultList
                }
            }

        })
    }
}