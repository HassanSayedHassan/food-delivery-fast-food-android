package com.ceh.fastfood

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ceh.fastfood.model.cartMenu.CartMenu
import io.paperdb.Paper

class ShoppingCart {
    companion object {

        fun addMenu(cartMenu: CartMenu) {
            val cart = getCart()
            val targetMenu = cart.singleOrNull { it.menu.id == cartMenu.menu.id }
            if (targetMenu == null) {
                cartMenu.qty++
                cart.add(cartMenu)
            } else {
                targetMenu.qty++
            }
            saveCart(cart)
        }

        fun getAmount(cardMenu: List<CartMenu>): Double {
            var totalAmount = 0.0
            getCart().forEach {
                totalAmount += it.menu.menu_price.toDouble() * it.qty


            }
            return totalAmount
        }

        fun removeMenu(cartMenu: CartMenu, context: Context) {
            val cart = getCart()
            val targetMenu = cart.singleOrNull { it.menu.id == cartMenu.menu.id }
            if (targetMenu != null) {
                if (targetMenu.qty > 0) {
                    Toast.makeText(context, "Great quantity", Toast.LENGTH_LONG).show()
                    targetMenu.qty--
                } else {
                    cart.remove(targetMenu)
                }
            }
            saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartMenu>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartMenu> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.qty
            }
            return cartSize
        }

        fun deleteShoppingCart() {
            Log.d("DeCart", "DeleteCart")
            Paper.book().delete("cart")
        }
    }
}