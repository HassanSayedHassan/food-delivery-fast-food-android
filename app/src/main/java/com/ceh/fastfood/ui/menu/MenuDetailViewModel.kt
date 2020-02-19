package com.ceh.fastfood.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceh.fastfood.api.AllAPI
import com.ceh.fastfood.model.menu.Menu
import com.ceh.fastfood.model.menu.MenuX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuDetailViewModel : ViewModel() {
    private val allAPI: AllAPI = AllAPI()
    var menuResults:MutableLiveData<Menu> = MutableLiveData()
    var menusLoadError:MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private var selectedMenu: MutableLiveData<MenuX> = MutableLiveData()

    fun getResults(): LiveData<Menu> = menuResults
    fun  getError(): LiveData<Boolean> = menusLoadError
    fun getLoading(): LiveData<Boolean> = loading

    fun getSelectedMenu():LiveData<MenuX> = selectedMenu
    fun  selectedMenu(menu: MenuX){
        selectedMenu.value = menu
        Log.d("Selected Menu", menu.toString())
    }

    fun loadResults(restaurant_id:String){
        loading.value = true
        val call = allAPI.getMenuByRestaurantResult(restaurant_id)
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
                    Log.d("Menu List", resultList.toString())
                    menuResults.value = resultList
                }
            }

        })
    }
}