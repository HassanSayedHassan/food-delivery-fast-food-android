package com.ceh.fastfood.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.MainActivity
import com.ceh.fastfood.R
import com.ceh.fastfood.ShoppingCart
import com.ceh.fastfood.model.cart.CartItem
import com.ceh.fastfood.model.menu.MenuX
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menu_by_category_item.view.*
import kotlin.coroutines.coroutineContext

class MenuByCategoryAdapter (var menusList:List<MenuX> = ArrayList()) : RecyclerView.Adapter<MenuByCategoryAdapter.MenuByCategoryViewHolder>() {

    var mClickListener: ClickListener? = null
    /*var cart: MutableList<Cart> = ArrayList()*/
    fun setOnClickListener(clickListener: ClickListener){
        this.mClickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuByCategoryViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.menu_by_category_item,parent,false)
        return  MenuByCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("Menu List:", menusList.size.toString())
        return menusList.size
    }

    override fun onBindViewHolder(holder: MenuByCategoryViewHolder, position: Int) {
        holder.bindFood(menusList.get(position))
    }

    fun updateList(menu:List<MenuX>){
        this.menusList = menu
        notifyDataSetChanged()
    }

    inner class MenuByCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view:View = itemView
        private lateinit var menusResult:MenuX
        init {
            itemView.setOnClickListener(this)
        }
        fun bindFood(menus: MenuX){
            this.menusResult = menus
            Picasso.get().load(menusResult.menu_photo)
                .placeholder(R.drawable.burrito_chicken_delicious)
                .into(view.menu_image_by_category)
            view.menu_id_by_category.text = menusResult.id.toString()
            view.menu_name_by_category.text = menusResult.menu_name
            view.menu_price_by_category.text = menusResult.menu_price
            view.restaurant_name_by_category.text = menusResult.user_detail_id.user.name
            view.restaurant_township_name.text = menusResult.user_detail_id.township.township_name

            var cartItem: MutableList<CartItem> = ArrayList()
            view.add_to_cart_btn_by_category.setOnClickListener{
               // Log.d("CartItemArray", cartItem.toString())
                Toast.makeText(view.context,menusResult.menu_name + " added to your cart", Toast.LENGTH_LONG).show()
                var menuId:String = view.menu_id_by_category.text.toString()
                var menuQty:Int = 1
                var menuPrice = menusResult.menu_price.toInt()
                var subTotal = menuPrice * menuQty
                Log.d("subTotal", subTotal.toString())

                /*cartItem.add(CartItem(menuId,menuQty,subTotal))
                Log.d("CartItem", cartItem.toString())


                cart.add(Cart(cartItem,1,"22-1-1997",5000.toString()))
                Log.d("Cart", cart.toString())*/
            }

            //view.delivered_township_names.text = menusResult.townships.get(0).township_name
        }
        override fun onClick(v: View?) {
            mClickListener?.onClick(menusResult)
        }
    }
    interface ClickListener{
        fun onClick(menu: MenuX)
    }
}