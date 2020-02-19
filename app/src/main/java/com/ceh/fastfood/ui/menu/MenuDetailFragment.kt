package com.ceh.fastfood.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.ceh.fastfood.adapter.MenuAdapter
import com.ceh.fastfood.adapter.MenuByCategoryAdapter
import com.ceh.fastfood.model.menu.Menu
import com.ceh.fastfood.model.menu.MenuX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.fragment_menu_detail.view.*

class MenuDetailFragment :Fragment(){
    private lateinit var menuByCategoryListAdapter: MenuByCategoryAdapter
    private lateinit var menuDetailViewModel: MenuDetailViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
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
                related_menu_of_these_restaurant_name.text = menu.user_detail_id?.user?.name
                add_to_cart_btn.setOnClickListener {
                    Toast.makeText(activity,menu.menu_name + " added to your cart", Toast.LENGTH_LONG).show()
                }
            }
        )
        viewManager = LinearLayoutManager(activity)
        menuByCategoryListAdapter = MenuByCategoryAdapter()
        menu_by_restaurant_recyclerview.adapter = menuByCategoryListAdapter
        menu_by_restaurant_recyclerview.layoutManager = viewManager
       // menuListAdapter.setOnClickListener(this)
        observeViewModel()
    }
    fun observeViewModel(){
        menuDetailViewModel = ViewModelProviders.of(this)
            .get(MenuDetailViewModel::class.java)
        menuDetailViewModel.getResults().observe(
            this, Observer<Menu>{ result ->
                menu_by_restaurant_recyclerview.visibility = View.VISIBLE
                menuByCategoryListAdapter.updateList(result.menus)
            }
        )
        menuDetailViewModel.getError().observe(
            this, Observer<Boolean>{ isError->
                if (isError){
                    txt_error_menu_by_category.visibility = View.VISIBLE
                    menu_by_restaurant_recyclerview.visibility = View.GONE
                }
                else{
                    txt_error_menu_by_category.visibility = View.GONE
                }
            }
        )
        menuDetailViewModel.getLoading().observe(
            this, Observer<Boolean>{isLoading ->
                loading_view_menu_by_category.visibility = (if (isLoading) View.VISIBLE else View.INVISIBLE)
                if (isLoading){
                    txt_error_menu_by_category.visibility = View.GONE
                    menu_by_restaurant_recyclerview.visibility = View.GONE
                }
            }
        )
    }
    override fun onResume() {
        super.onResume()
        menuDetailViewModel.loadResults("1")
    }
}