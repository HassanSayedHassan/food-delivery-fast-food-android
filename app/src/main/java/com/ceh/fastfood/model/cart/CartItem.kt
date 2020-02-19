package com.ceh.fastfood.model.cart

import com.ceh.fastfood.model.menu.MenuX

data class CartItem(
    val menu: MenuX,
    var qty:Int = 0
)