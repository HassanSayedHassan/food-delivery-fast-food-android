package com.ceh.fastfood.model.cartMenu

import com.ceh.fastfood.model.menu.MenuX

data class CartMenu(
    val menu: MenuX,
    var qty:Int = 0
)