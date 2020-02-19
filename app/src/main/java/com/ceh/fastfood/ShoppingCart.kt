package com.ceh.fastfood

import android.content.Context
import com.ceh.fastfood.model.cart.CartItem
import io.paperdb.Paper

class ShoppingCart {

    companion object {
        fun addItem(cartItem: CartItem) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.menu.id == cartItem.menu.id }
            if (targetItem == null) {
                cartItem.qty++
                cart.add(cartItem)
            } else {
                targetItem.qty++
            }
            saveCart(cart)
        }

        fun removeItem(cartItem: CartItem, context: Context) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.menu.id == cartItem.menu.id }
            if (targetItem != null) {
                if (targetItem.qty > 0) {
                    targetItem.qty--
                } else {
                    cart.remove(targetItem)
                }
            }

            saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.qty
            }

            return cartSize
        }
    }

}