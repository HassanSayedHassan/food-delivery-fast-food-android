package com.ceh.fastfood.ui.viewcart

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceh.fastfood.R
import com.ceh.fastfood.ShoppingCart
import com.ceh.fastfood.adapter.ViewCartAdapter
import com.ceh.fastfood.model.order.OrderDetailCart
import com.ceh.fastfood.ui.home.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_view_cart.*
import java.time.LocalDate


class ViewCartFragment : Fragment() {

    lateinit var viewCartAdapter: ViewCartAdapter
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_view_cart, container, false)
        var btnOrder = root.findViewById<Button>(R.id.btnOrder)


        var totalCalculate = ShoppingCart.getCart()
//            .fold(0.toDouble()) { acc, cartMenu ->
//                acc + cartMenu.quantity.times(cartMenu.menu.menu_price!!.toDouble())
//            }
        Log.d("Total>>>>", totalCalculate.toString())

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        val totalAmount = ShoppingCart.getAmount(ShoppingCart.getCart())

        total_all.text = totalAmount.toString()

        viewCartAdapter = ViewCartAdapter(activity!!, ShoppingCart.getCart())
        Log.d("DeleCart", ShoppingCart.getCart().toString())

        //Log.d("Total cart view>>>>", ShoppingCart.getCart().toString())
        //Log.d("Total amount>>>>", ShoppingCart.getAmount(ShoppingCart.getCart()).toString())
        viewCartAdapter.notifyDataSetChanged()

        invoice_recycler.adapter = viewCartAdapter
        invoice_recycler.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        val currentDate = LocalDate.now()
        var cart = ArrayList<OrderDetailCart>()
        ShoppingCart.getCart().forEach {
            var menuId = it.menu.id
            var qty = it.qty;
            var price = it.menu.menu_price.toInt();
            var subtoal =  price * qty;

            cart.add(OrderDetailCart(menuId,qty,subtoal.toString()))
        }
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonTutsArrayPretty: String = gsonPretty.toJson(cart)
        Log.d("JsonCart", jsonTutsArrayPretty);
        btnOrder.setOnClickListener{ view: View ->
            viewModel.postOrder(4, currentDate, totalAmount.toString(),jsonTutsArrayPretty)
            viewModel.getResponseMessage().observe(
                viewLifecycleOwner,
                Observer {

                    Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
                        .show()
                    viewModel.responseMessage = MutableLiveData()
                    //Log.d("Res", it.toString())
                    ShoppingCart.deleteShoppingCart();
                    Log.d("DeletedCart", ShoppingCart.getCart().toString())
                    viewCartAdapter.updateList(ShoppingCart.getCart())
                    totalView.visibility = View.GONE
                    emptyCart.visibility = View.VISIBLE/*
                    btnViewItem.visibility = View.VISIBLE
                    btnViewItem.setOnClickListener {
                        var action = ViewCartFragmentDirections.actionViewCartFragmentToNavCategory()
                        findNavController().navigate(action)
                    }*/
                }
            )
        }

    }

    fun receiveMsg(msg: String?) {
        Log.d("receiveMessage", msg)
    }
}