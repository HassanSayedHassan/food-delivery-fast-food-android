package com.ceh.fastfood.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.ceh.fastfood.model.menu.MenuX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuAdapter (var menusList:List<MenuX> = ArrayList()) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    var mClickListener: ClickListener? = null
    fun setOnClickListener(clickListener: ClickListener){
        this.mClickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent,false)
        return  MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        //Log.d("Menu List:", menusList.size.toString())
        return menusList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindFood(menusList.get(position))
    }

    fun updateList(menu:List<MenuX>){
        this.menusList = menu
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view:View = itemView
        private lateinit var menusResult:MenuX
        init {
            itemView.setOnClickListener(this)
        }
        fun bindFood(menus: MenuX){
            this.menusResult = menus
            Picasso.get().load(menusResult.menu_photo)
                .placeholder(R.drawable.burrito_chicken_delicious)
                .into(view.menu_image)
            view.menu_name.text = menusResult.menu_name
            view.menu_price.text = menusResult.menu_price
        }
        override fun onClick(v: View?) {
            mClickListener?.onClick(menusResult)
        }
    }
    interface ClickListener{
        fun onClick(menu: MenuX)
    }
}