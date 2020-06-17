package com.ceh.fastfood.ui.category

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceh.fastfood.R
import com.ceh.fastfood.adapter.CategoryAdapter
import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.category.CategoryX
import com.ceh.fastfood.ui.home.HomeViewModel
import com.ceh.fastfood.ui.menu.MenuViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoryFragment : Fragment(), CategoryAdapter.ClickListener{

    private lateinit var categoryListAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var viewCategoryManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCategoryManager = GridLayoutManager(activity,2)
        categoryListAdapter = CategoryAdapter()
        category_one_recyclerview.adapter = categoryListAdapter
        category_one_recyclerview.layoutManager = viewCategoryManager
        categoryListAdapter.setOnClickListener(this)
        observeCategoryViewModel()
    }
    fun observeCategoryViewModel(){
        categoryViewModel = ViewModelProviders.of(this)
            .get(CategoryViewModel::class.java)
        categoryViewModel.getCategoryResults().observe(
            this, Observer<Category>{ category_result ->
                category_one_recyclerview.visibility = View.VISIBLE
                categoryListAdapter.updateList(category_result.categories)
                /* Log.d("Category", category_result.categories.toString())*/
            }
        )
        categoryViewModel.getCategoryError().observe(
            this, Observer<Boolean>{ isError->
                if (isError){
                    txt_error_category.visibility = View.VISIBLE
                    category_one_recyclerview.visibility = View.GONE
                }
                else{
                    txt_error_category.visibility = View.GONE
                }
            }
        )
        categoryViewModel.getCategoryLoading().observe(
            this, Observer<Boolean>{isLoading ->
                loading_view_category.visibility = (if (isLoading) View.VISIBLE else View.INVISIBLE)
                if (isLoading){
                    txt_error_category.visibility = View.GONE
                    category_one_recyclerview.visibility = View.GONE
                }
            }
        )
    }
    override fun onResume() {
        super.onResume()
        categoryViewModel.loadCategoryResults()
    }

    override fun onClick(categories: CategoryX) {
        if (!TextUtils.isEmpty(categories.id.toString())) {
            var categoryID = categories.id
            var action = CategoryFragmentDirections.actionNavCategoryToMenuFragment(categoryID)
            findNavController().navigate(action)
        }
    }
}