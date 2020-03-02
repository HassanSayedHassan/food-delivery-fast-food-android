package com.ceh.fastfood.ui.home
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ceh.fastfood.R
import com.ceh.fastfood.adapter.CategoryAdapter
import com.ceh.fastfood.adapter.MenuAdapter
import com.ceh.fastfood.adapter.SliderImageAdapter
import com.ceh.fastfood.adapter.SliderImageModel
import com.ceh.fastfood.model.category.Category
import com.ceh.fastfood.model.category.CategoryX
import com.ceh.fastfood.model.menu.Menu
import com.ceh.fastfood.model.menu.MenuX
import com.ceh.fastfood.ui.menu.MenuDetailViewModel
import com.ceh.fastfood.ui.menu.MenuFragment
import com.ceh.fastfood.ui.menu.MenuViewModel
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), MenuAdapter.ClickListener, CategoryAdapter.ClickListener{

    private lateinit var menuListAdapter: MenuAdapter
    private lateinit var categoryListAdapter: CategoryAdapter

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewCategoryManager: RecyclerView.LayoutManager

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var imageModelArrayList: ArrayList<SliderImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.cooked_seafoods, R.drawable.beef_steak_with_sauce, R.drawable.burrito_chicken_delicious)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        viewManager = LinearLayoutManager(activity)
        menuListAdapter = MenuAdapter()
        menu_recyclerview.adapter = menuListAdapter
        menu_recyclerview.layoutManager = viewManager
        menuListAdapter.setOnClickListener(this)
        observeViewModel()

        viewCategoryManager = GridLayoutManager(activity,2)
        categoryListAdapter = CategoryAdapter()
        category_recyclerview.adapter = categoryListAdapter
        category_recyclerview.layoutManager = viewCategoryManager
        categoryListAdapter.setOnClickListener(this)
        observeCategoryViewModel()
        init()
    }
    private fun populateList(): ArrayList<SliderImageModel> {

        val list = ArrayList<SliderImageModel>()

        for (i in 0..2) {
            val imageModel = SliderImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }

    private fun init() {

        mPager = pager as ViewPager
        mPager!!.adapter = SliderImageAdapter(context!!, this.imageModelArrayList!!)
        val indicator = indicator as CirclePageIndicator

        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.setRadius(5 * density)

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position

            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })

    }

    companion object {

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }



    fun observeViewModel(){
        homeViewModel = ViewModelProviders.of(this)
            .get(HomeViewModel::class.java)
        homeViewModel.getResults().observe(
            this, Observer<Menu>{result ->
                menu_recyclerview.visibility = View.VISIBLE
                menuListAdapter.updateList(result.menus)
                Log.d("Result Menus", result.menus.toString())
            }
        )
        homeViewModel.getError().observe(
            this, Observer<Boolean>{ isError->
                if (isError){
                    txt_error.visibility = View.VISIBLE
                    menu_recyclerview.visibility = View.GONE
                }
                else{
                    txt_error.visibility = View.GONE
                }
            }
        )
        homeViewModel.getLoading().observe(
            this, Observer<Boolean>{isLoading ->
                loadingView.visibility = (if (isLoading) View.VISIBLE else View.INVISIBLE)
                if (isLoading){
                    txt_error.visibility = View.GONE
                    menu_recyclerview.visibility = View.GONE
                }
            }
        )
    }
    fun observeCategoryViewModel(){
        homeViewModel = ViewModelProviders.of(this)
            .get(HomeViewModel::class.java)
        homeViewModel.getCategoryResults().observe(
            this, Observer<Category>{category_result ->
                category_recyclerview.visibility = View.VISIBLE
                categoryListAdapter.updateList(category_result.categories)
               /* Log.d("Category", category_result.categories.toString())*/
            }
        )
        homeViewModel.getError().observe(
            this, Observer<Boolean>{ isError->
                if (isError){
                    txt_error.visibility = View.VISIBLE
                    category_recyclerview.visibility = View.GONE
                }
                else{
                    txt_error.visibility = View.GONE
                }
            }
        )
        homeViewModel.getLoading().observe(
            this, Observer<Boolean>{isLoading ->
                loadingView.visibility = (if (isLoading) View.VISIBLE else View.INVISIBLE)
                if (isLoading){
                    txt_error.visibility = View.GONE
                    category_recyclerview.visibility = View.GONE
                }
            }
        )
    }
    override fun onResume() {
        super.onResume()
        homeViewModel.loadResults()
        homeViewModel.loadCategoryResults()
    }

    override fun onClick(menu: MenuX) {
        if (!TextUtils.isEmpty(menu.menu_photo)) {
            val menuDetailViewModel: MenuDetailViewModel =
                ViewModelProviders.of(activity!!).get(MenuDetailViewModel::class.java)
            menuDetailViewModel.selectedMenu(menu)
            Toast.makeText(context, menu.menu_name, Toast.LENGTH_LONG).show()
            view!!.findNavController().navigate(R.id.action_nav_home_to_menuDetailFragment2)
            /*val nextFrag = MenuDetailFragment()
            activity!!.supportFragmentManager.beginTransaction()`
                .replace(R.id.screen_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()*/
        }
    }

    override fun onClick(categories: CategoryX) {
        if (!TextUtils.isEmpty(categories.id.toString())) {
            val menuViewModel: MenuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel::class.java)
            menuViewModel.loadResults(categories.id.toString())
            //Toast.makeText(context, categories.id, Toast.LENGTH_LONG).show()
            view!!.findNavController().navigate(R.id.action_nav_home_to_menuFragment)
            /*val nextFrag = MenuDetailFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.screen_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()*/
        }
    }
}