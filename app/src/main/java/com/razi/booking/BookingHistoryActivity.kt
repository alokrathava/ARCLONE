package com.razi.booking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.adapters.OrderAdapter
import com.data.Order
import com.network.ApiService
import com.network.RetroClass
import com.razi.furnitar.databinding.ActivityBookingHistoryBinding
import com.utils.Config
import com.utils.toast
import kotlinx.coroutines.launch

class BookingHistoryActivity : AppCompatActivity(), OrderAdapter.OrderInterface {


    companion object {
        private const val TAG = "BookingActivity"
    }

    private lateinit var binding: ActivityBookingHistoryBinding
    private val context = this

    private val bookingHistoryAdapter = OrderAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityBookingHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleToolbar()
        init()
        getOrders()
    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private fun handleToolbar() {
        binding.includedToolbar.title.text = "Order History"
        binding.includedToolbar.backBtn.setOnClickListener { finish() }
    }

    private fun init() {
        binding.recyclerView.adapter = bookingHistoryAdapter
    }


    private fun getOrders() {
        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.getOrderHistory(Config.user_id)

                Log.d(TAG, "getRestaurantDetails: $response")

                bookingHistoryAdapter.submitList(response.orders)

            } catch (exception: Exception) {
                toast(exception.message.toString())
            }
        }
    }

    override fun onClick(order: Order) {


    }

}