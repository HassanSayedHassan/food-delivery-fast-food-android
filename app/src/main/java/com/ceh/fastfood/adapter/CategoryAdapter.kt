package com.ceh.fastfood.adapter

import com.ceh.fastfood.model.category.CategoryX


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter (var categoryList:List<CategoryX> = ArrayList()) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var mClickListener: ClickListener? = null
    fun setOnClickListener(clickListener: ClickListener){
        this.mClickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return  CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("Category List:", categoryList.size.toString())
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindCategory(categoryList.get(position))
    }

    fun updateList(category:List<CategoryX>){
        this.categoryList = category
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view:View = itemView
        private lateinit var categoriesResult:CategoryX
        init {
            itemView.setOnClickListener(this)
        }
        fun bindCategory(categories: CategoryX){
            this.categoriesResult = categories
            Picasso.get().load(categoriesResult.category_photo)
                .placeholder(R.drawable.burrito_chicken_delicious)
                .into(view.category_image)
            view.category_name.text = categoriesResult.category_name
        }
        override fun onClick(v: View?) {
            mClickListener?.onClick(categoriesResult)
        }
    }
    interface ClickListener{
        fun onClick(categories: CategoryX)
    }
}