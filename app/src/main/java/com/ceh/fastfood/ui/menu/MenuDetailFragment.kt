package com.ceh.fastfood.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ceh.fastfood.R
import com.ceh.fastfood.model.menu.MenuX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.fragment_menu_detail.view.*
class MenuDetailFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Menu's Detail"
        return inflater.inflate(R.layout .fragment_menu_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuDetailViewModel:MenuDetailViewModel = ViewModelProviders.of(activity!!).get(MenuDetailViewModel::class.java)
        Log.d("Menu Detail", menuDetailViewModel.getSelectedMenu().toString())
        menuDetailViewModel.getSelectedMenu().observe(
            viewLifecycleOwner, Observer<MenuX>{ menu ->
                Log.d("Detail Name", "Name")
                Picasso.get().load(menu.menu_photo)
                    .placeholder(R.drawable.burrito_chicken_delicious)
                    .into(view.menu_detail_image)
                menu_detail_name.text = menu.menu_name
                menu_detail_restaurant_name.text = menu.user_detail_id?.user?.name
                menu_detail_description.text = menu.description
                menu_detail_price.text = menu.menu_price
            }
        )
    }
}