package com.razi.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.adapters.CartAdapter
import com.data.Cart
import com.network.ApiService
import com.network.RetroClass
import com.razi.furnitar.DashboardActivity
import com.razi.furnitar.databinding.ActivityCartActivityBinding
import com.razi.furnitar.databinding.ActivityMyCartBinding
import com.razi.furnitar.databinding.ActivityProductDetailsBinding
import com.utils.Config
import com.utils.toast
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity(), CartAdapter.CartInterface {

    companion object {
        private const val TAG = "CartActivity"
    }

    private lateinit var binding: ActivityMyCartBinding
    private val cartAdapter = CartAdapter(this)

    private val context = this

    private var amount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        handleToolbar()
        init()
        clickListener()
        getCartList()
        getGrandTotal()
    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private fun handleToolbar() {
        binding.includedToolbar.title.text = "Cart"
        binding.includedToolbar.backBtn.setOnClickListener { finish() }
    }


    /*--------------------------------- Init --------------------------------*/


    private fun init() {
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = cartAdapter
        }
    }


    /*--------------------------------- Click Listener --------------------------------*/


    private fun clickListener() {
        binding.proceedToPay.setOnClickListener {
            val intent = Intent(context, OrderActivity::class.java).also {
                it.putExtra("amount", amount)
            }
            startActivity(intent)
        }
    }

    /*--------------------------------- Get Cart List --------------------------------*/


    private fun getCartList() {

        binding.loadingSpinner.isVisible = true
        lifecycleScope.launch {
            try {

                binding.loadingSpinner.isVisible = false
                binding.notFound.isVisible = false

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.getCartData(Config.user_id)

                Log.d(TAG, "getRestaurantDetails: $response")

                cartAdapter.submitList(response.carts)

            } catch (exception: Exception) {
                binding.loadingSpinner.isVisible = false
                binding.container.isVisible = false
                binding.notFound.isVisible = true
                cartAdapter.submitList(emptyList())
                Log.d(TAG, "getCartList: ${exception.message}")
            }
        }
    }

    /*--------------------------------- Get Grand Total --------------------------------*/


    private fun getGrandTotal() {

        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.getGrandTotal(Config.user_id)

                Log.d(TAG, "getRestaurantDetails: $response")

                binding.apply {

                    amount = response.grandTotal?.total ?: 0
                    amountTv.text = "Rs. ${amount}"
                }

            } catch (exception: Exception) {
                Log.d(TAG, "getGrandTotal: ${exception.message}")

//                toast(exception.message.toString())
            }
        }
    }


    /*--------------------------------- Handle Interface --------------------------------*/

    override fun onDelete(cart: Cart) {
        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.removeToCart(cart.cartId)

                Log.d(TAG, "getRestaurantDetails: $response")

                getCartList()
                getGrandTotal()

            } catch (exception: Exception) {

//                toast(exception.message.toString())
                Log.d(TAG, "getGrandTotal: ${exception.message}")

            }
        }

    }

    override fun onChange(cart: Cart, qty: Int) {

        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.updateToCart(cart.cartId, qty)

                Log.d(TAG, "getRestaurantDetails: $response")

                getGrandTotal()

            } catch (exception: Exception) {
                Log.d(TAG, "getGrandTotal: ${exception.message}")

//                toast(exception.message.toString())

            }
        }
    }

}