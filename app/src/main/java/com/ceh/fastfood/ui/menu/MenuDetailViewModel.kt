package com.ceh.fastfood.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceh.fastfood.model.menu.MenuX

class MenuDetailViewModel : ViewModel() {
    private var selectedMenu: MutableLiveData<MenuX> = MutableLiveData()
    fun getSelectedMenu():LiveData<MenuX> = selectedMenu
    fun  selectedMenu(menu: MenuX){
        selectedMenu.value = menu
        Log.d("Selected Menu", menu.toString())
    }
}