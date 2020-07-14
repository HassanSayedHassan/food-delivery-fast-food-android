package com.ceh.fastfood

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ceh.fastfood.adapter.SliderImageModel
import com.ceh.fastfood.ui.viewcart.ViewCartFragment
import com.google.android.material.navigation.NavigationView
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var imageModelArrayList: ArrayList<SliderImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.cooked_seafoods, R.drawable.beef_steak_with_sauce, R.drawable.burrito_chicken_delicious)
    var cart_count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*if (savedInstanceState == null){
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.screen_container, homeFragment)
                .commit()
        }*/

        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_category, R.id.nav_about_us, R.id.nav_contact_us
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Paper.init(baseContext)
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.view_cart)
        menuItem.setIcon(
            Converter.convertLayoutToImage(
                this@MainActivity,
                ShoppingCart.getShoppingCartSize(),
                R.drawable.ic_shopping_cart_white
            )
        )
        Log.d("Count", ShoppingCart.getShoppingCartSize().toString())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.view_cart) {
            supportFragmentManager.beginTransaction().replace(R.id.screen_container, ViewCartFragment()).commit()
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
