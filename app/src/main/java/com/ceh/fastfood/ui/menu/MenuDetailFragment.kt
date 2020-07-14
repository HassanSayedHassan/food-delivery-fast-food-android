package com.ceh.fastfood.ui.menu

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.ceh.fastfood.adapter.MenuByCategoryAdapter
import com.ceh.fastfood.model.menu.Menu
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_menu_detail.*


class MenuDetailFragment :Fragment(){
    private lateinit var menuByCategoryListAdapter: MenuByCategoryAdapter
    private lateinit var menuDetailViewModel: MenuDetailViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Paper.init(context)
        (activity as AppCompatActivity).supportActionBar?.title = "Menu's Detail"
        var root = inflater.inflate(R.layout .fragment_menu_detail, container, false)
        /*var viewCart = root.findViewById<Button>(R.id.view_cart)
        viewCart.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_menuDetailFragment2_to_viewCartFragment)
        }*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuDetailViewModel:MenuDetailViewModel = ViewModelProviders.of(activity!!).get(MenuDetailViewModel::class.java)
        Log.d("Menu Detail", menuDetailViewModel.getSelectedMenu().toString())
        var baseUrl = "http://food-delivery-api.chaneihmwe.com/"
        /*menuDetailViewModel.getSelectedMenu().observe(
            viewLifecycleOwner, Observer<MenuX>{ menu ->
                Log.d("Detail Name", "Name")
                Picasso.get().load(baseUrl+menu.menu_image)
                    .placeholder(R.drawable.burrito_chicken_delicious)
                    .into(view.menu_detail_image)
                menu_detail_name.text = menu.menu_name
                menu_detail_restaurant_name.text = menu.restaurant?.user?.name
                menu_detail_description.text = menu.description
                menu_detail_price.text = menu.menu_price
                related_menu_of_these_restaurant_name.text = menu.restaurant?.user?.name
                add_to_cart_btn.setOnClickListener {
                    Toast.makeText(activity,menu.menu_name + " added to your cart", Toast.LENGTH_LONG).show()
                    Toast.makeText(activity,menu.menu_name + " added to your cart", Toast.LENGTH_LONG).show()
                }
            }
        )*/
        viewManager = LinearLayoutManager(activity)
        menuByCategoryListAdapter = MenuByCategoryAdapter()
        menu_by_restaurant_recyclerview.adapter = menuByCategoryListAdapter
        menu_by_restaurant_recyclerview.layoutManager = viewManager
       // menuListAdapter.setOnClickListener(this)
        var messageArgs = arguments?.let { MenuDetailFragmentArgs.fromBundle(it) }
        var restaurantID = messageArgs?.restaurantID
        observeViewModel(restaurantID!!)
    }
    fun observeViewModel(restaurantID:Int){
        menuDetailViewModel = ViewModelProviders.of(this)
            .get(MenuDetailViewModel::class.java)
        menuDetailViewModel.loadResults(restaurantID)
        menuDetailViewModel.getResults().observe(
            this, Observer<Menu>{ result ->
                menu_by_restaurant_recyclerview.visibility = View.VISIBLE
                Log.d("MenuDetail", result.menus.toString())
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
        var messageArgs = arguments?.let { MenuDetailFragmentArgs.fromBundle(it) }
        var restaurantID = messageArgs?.restaurantID
        menuDetailViewModel.loadResults(restaurantID!!)
    }



}