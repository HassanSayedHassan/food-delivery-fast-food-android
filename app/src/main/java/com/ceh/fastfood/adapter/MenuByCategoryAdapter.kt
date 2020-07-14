package com.ceh.fastfood.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.MainActivity
import com.ceh.fastfood.R
import com.ceh.fastfood.ShoppingCart
import com.ceh.fastfood.model.cartMenu.CartMenu
import com.ceh.fastfood.model.menu.MenuX
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.menu_by_category_item.*
import kotlinx.android.synthetic.main.menu_by_category_item.view.*
import kotlin.collections.ArrayList

class MenuByCategoryAdapter (var menusList:List<MenuX> = ArrayList()) : RecyclerView.Adapter<MenuByCategoryAdapter.MenuByCategoryViewHolder>() {

    var mClickListener: ClickListener? = null
    /*var cart: MutableList<Cart> = ArrayList()*/
    var count = 0
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
            @SuppressLint("CheckResult")
            fun bindFood(menus: MenuX){
            var baseUrl = "http://food-delivery-api.chaneihmwe.com/"
            this.menusResult = menus
            Picasso.get().load(baseUrl+menusResult.menu_image)
                .placeholder(R.drawable.burrito_chicken_delicious)
                .into(view.menu_image_by_category)
            view.menu_id_by_category.text = menusResult.id.toString()
            view.menu_name_by_category.text = menusResult.menu_name
            view.menu_price_by_category.text = menusResult.menu_price
            view.restaurant_name_by_category.text = menusResult.restaurant.user.name
            view.restaurant_township_name.text = menusResult.restaurant.township.township_name

                Observable.create(ObservableOnSubscribe<MutableList<CartMenu>> {

                /*view.btn_increase.setOnClickListener { v ->

                    count += 1
                    view.qty.text = count.toString()
                    Log.d("Count>>>>", count.toString())

                    val menuItem = CartMenu(menus)
                    ShoppingCart.addMenu(menuItem)
                    Snackbar.make(
                        (view.context as MainActivity).menu_layout,
                        "${menus.menu_name} added to your cart",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(ShoppingCart.getCart())
                }*/

                /*view.btn_decrease.setOnClickListener { v ->

                    count--
                    if (count > 0)
                        view.qty.text = count.toString()
                    else
                        view.qty.text = "0"

                    val menuItem = CartMenu(menus)
                    ShoppingCart.removeMenu(menuItem, view.context)
                    Snackbar.make(
                        (view.context as MainActivity).menu_layout,
                        "${menus.menu_name} removed from your cart",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(ShoppingCart.getCart())
                }*/
                view.add_to_cart_btn_by_category.setOnClickListener { v ->

                    val menuItem = CartMenu(menus)
                    ShoppingCart.addMenu(menuItem)
                    Snackbar.make(
                        (view.context as MainActivity).menu_layout,
                        "${menus.menu_name} added to your cart",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(ShoppingCart.getCart())
                }

            }).subscribe { cart ->
                var qty = 0
                cart.forEach { cartMenu ->
                    qty += cartMenu.qty
                }
                Toast.makeText(view.context, "Cart size ${ShoppingCart.getShoppingCartSize()}", Toast.LENGTH_LONG).show()
            }
        }
        override fun onClick(v: View?) {
            mClickListener?.onClick(menusResult)
        }
    }
    interface ClickListener{
        fun onClick(menu: MenuX)
    }
}