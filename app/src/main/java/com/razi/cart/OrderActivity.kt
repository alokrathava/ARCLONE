package com.razi.cart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.network.ApiService
import com.network.RetroClass
import com.razi.furnitar.DashboardActivity
import com.razi.furnitar.databinding.ActivityOrderBinding
import com.utils.Config
import com.utils.startNewActivity
import com.utils.toast
import kotlinx.coroutines.launch

class OrderActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OrderActivity"
    }

    private lateinit var binding: ActivityOrderBinding
    private var amount = 0

    private lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        handleToolbar()
        init()
        clickListener()

    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private fun handleToolbar() {
        binding.includedToolbar.title.text = "Confirm Order Details"
        binding.includedToolbar.backBtn.setOnClickListener { finish() }
    }


    /*--------------------------------- Init --------------------------------*/


    private fun init() {
        amount = intent.getIntExtra("amount", 0)
        binding.tvAmount.setText(amount.toString())
    }


    /*--------------------------------- Click Listener --------------------------------*/


    private fun clickListener() {
        binding.btnOrder.setOnClickListener {

            address = binding.edtAddress.text.toString().trim()

            if (amount <= 0) {
                toast("Invalid Amount")
                return@setOnClickListener
            }


            if (address.isEmpty()) {
                toast("Invalid Address")
                return@setOnClickListener
            }

            placeOrder()
        }
    }

    /*--------------------------------- Get Cart List --------------------------------*/


    private fun placeOrder() {

        binding.loadingSpinner.isVisible = true
        lifecycleScope.launch {
            try {

                binding.loadingSpinner.isVisible = false

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.placeOrder(Config.user_id, address)

                Log.d(TAG, "getRestaurantDetails: $response")

                startNewActivity(DashboardActivity::class.java)

            } catch (exception: Exception) {
                binding.loadingSpinner.isVisible = false
                Log.d(TAG, "getCartList: ${exception.message}")
            }
        }
    }


}