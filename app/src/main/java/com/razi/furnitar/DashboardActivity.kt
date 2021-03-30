package com.razi.furnitar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.adapters.ProductAdapter
import com.data.Product
import com.google.android.material.navigation.NavigationView
import com.network.ApiService
import com.network.RetroClass
import com.razi.booking.BookingHistoryActivity
import com.razi.cart.CartActivity
import com.razi.furnitar.databinding.ActivityDashboardBinding
import com.utils.SharedPrefManager
import com.utils.afterTextChange
import com.utils.openActivity
import com.utils.toast
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity(), ProductAdapter.ProductInterface, NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val TAG = "DashboardActivity"
    }

    private lateinit var binding: ActivityDashboardBinding
    private val context = this
    private val productAdapter = ProductAdapter(this)
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var sharedPrefManager: SharedPrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getProductList()
    }


    private fun init() {


        sharedPrefManager = SharedPrefManager(context)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = productAdapter
        }

        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close)

        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(binding.includedToolbar.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_toggle)

        binding.nav.setNavigationItemSelectedListener(this)



        binding.edtSearchbar.editText!!.afterTextChange {
            productAdapter.filter.filter(it)
        }

    }


    private fun getProductList() {

        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.getProductList()

                Log.d(TAG, "getRestaurantDetails: $response")

                productAdapter.modifyList(response.products!!)

            } catch (exception: Exception) {

                toast(exception.message.toString())
            }
        }
    }


    override fun onClick(product: Product) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("product_id", product.productId.toInt())
        startActivity(intent)
    }


    /*----------------------------- On Option Item Selected ----------------------------*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }


    /*----------------------------- On Navigation Item Selected ----------------------------*/
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                openActivity(DashboardActivity::class.java)
                true
            }

            R.id.cart -> {
                openActivity(CartActivity::class.java)
                true
            }

            R.id.booking_history -> {
                openActivity(BookingHistoryActivity::class.java)
                true
            }


            R.id.logout -> {
                sharedPrefManager.clear()
                openActivity(Login::class.java)
                true
            }
            else -> false
        }
    }

}