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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.ceh.fastfood.adapter.CategoryAdapter
import com.ceh.fastfood.adapter.MenuByCategoryAdapter
import com.ceh.fastfood.model.menu.Menu
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_menu_by_category.*

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var menuByCategoryListAdapter: MenuByCategoryAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
//    private lateinit var categoryID: String



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Menus By Category"
        val root = inflater.inflate(R.layout.fragment_menu_by_category, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)
        menuByCategoryListAdapter = MenuByCategoryAdapter()
        menu_by_category_recyclerview.adapter = menuByCategoryListAdapter
        menu_by_category_recyclerview.layoutManager = viewManager
        var messageArgs = arguments?.let { MenuFragmentArgs.fromBundle(it) }
        var categoryID = messageArgs?.categoryID
        observeMenuByCategoryViewModel(categoryID!!)
    }
    fun observeMenuByCategoryViewModel(categoryID:Int){
        menuViewModel = ViewModelProviders.of(this)
            .get(MenuViewModel::class.java)
        menuViewModel.loadResults(categoryID)
        menuViewModel.getResults().observe(
            this, Observer<Menu>{ result ->
                menu_by_category_recyclerview.visibility = View.VISIBLE
                menuByCategoryListAdapter.updateList(result.menus)
                Log.d("MenuByCategory", result.menus.toString())
            }
        )
        menuViewModel.getError().observe(
            this, Observer<Boolean>{ isError->
                if (isError){
                    txt_menu_error.visibility = View.VISIBLE
                    menu_recyclerview.visibility = View.GONE
                }
                else{
                    txt_menu_error.visibility = View.GONE
                }
            }
        )
        menuViewModel.getLoading().observe(
            this, Observer<Boolean>{isLoading ->
                loadingMenuView.visibility = (if (isLoading) View.VISIBLE else View.INVISIBLE)
                if (isLoading){
                    txt_menu_error.visibility = View.GONE
                    menu_by_category_recyclerview.visibility = View.GONE
                }
            }
        )


    }
    override fun onResume() {
        super.onResume()
        var messageArgs = arguments?.let { MenuFragmentArgs.fromBundle(it) }
        var categoryID = messageArgs?.categoryID
      menuViewModel.loadResults(categoryID!!)
    }
}