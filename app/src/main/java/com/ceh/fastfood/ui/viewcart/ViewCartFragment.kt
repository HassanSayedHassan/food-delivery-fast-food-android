package com.ceh.fastfood.ui.viewcart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceh.fastfood.R
import com.ceh.fastfood.ShoppingCart
import com.ceh.fastfood.adapter.ViewCartAdapter
import kotlinx.android.synthetic.main.fragment_view_cart.*

class ViewCartFragment : Fragment() {

    lateinit var viewCartAdapter: ViewCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_view_cart, container, false)
        var btnOrder = root.findViewById<Button>(R.id.btnOrder)
        /*btnOrder.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_invoiceFragment_to_getCustomerDetailsFragment)
        }*/

        var totalCalculate = ShoppingCart.getCart()
//            .fold(0.toDouble()) { acc, cartMenu ->
//                acc + cartMenu.quantity.times(cartMenu.menu.menu_price!!.toDouble())
//            }
        Log.d("Total>>>>", totalCalculate.toString())

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalAmount = ShoppingCart.getAmount(ShoppingCart.getCart())

        total_all.text = totalAmount.toString()

        viewCartAdapter = ViewCartAdapter(activity!!, ShoppingCart.getCart())

        Log.d("Total cart view>>>>", ShoppingCart.getCart().toString())
        Log.d("Total amount>>>>", ShoppingCart.getAmount(ShoppingCart.getCart()).toString())
        viewCartAdapter.notifyDataSetChanged()

        invoice_recycler.adapter = viewCartAdapter
        invoice_recycler.layoutManager = LinearLayoutManager(context)

    }
}