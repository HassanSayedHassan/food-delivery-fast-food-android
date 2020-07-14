package com.ceh.fastfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.ceh.fastfood.model.cartMenu.CartMenu
import com.ceh.fastfood.model.category.CategoryX
import com.ceh.fastfood.model.menu.Menu
import com.ceh.fastfood.model.order.OrderDetailCart
import kotlinx.android.synthetic.main.view_cart.view.*

class ViewCartAdapter(var context: Context, var cartMenu: List<CartMenu>)
    : RecyclerView.Adapter<ViewCartAdapter.ViewCartViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCartViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.view_cart, parent, false)
        return ViewCartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartMenu.size
    }

    override fun onBindViewHolder(holder: ViewCartViewHolder, position: Int) {
        holder.bindMenuCartList(cartMenu[position])
    }

    fun updateList(cart:List<CartMenu>){
        this.cartMenu = cart
        notifyDataSetChanged()
    }

    class ViewCartViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindMenuCartList(cartMenu: CartMenu) {
            itemView.menu_name.text = cartMenu.menu.menu_name
            itemView.text_quantity.text = cartMenu.qty.toString()

            var totalPrice = cartMenu.qty.times(cartMenu.menu.menu_price!!.toDouble())
            itemView.total_price.text = "${totalPrice}"
        }

    }

}