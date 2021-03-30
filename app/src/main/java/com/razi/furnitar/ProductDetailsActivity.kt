package com.razi.furnitar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.data.Product
import com.network.ApiService
import com.network.RetroClass
import com.razi.cart.CartActivity
import com.razi.cart.OrderActivity
import com.razi.furnitar.databinding.ActivityProductDetailsBinding
import com.utils.Config
import com.utils.Config.imageUrl
import com.utils.openActivity
import com.utils.toast
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ProductDetailsActivity"
    }

    private lateinit var binding: ActivityProductDetailsBinding
    private val context = this
    private var productId = 0
    private var price = 0
    private var assetUrl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra("product_id", 0)

        clickListener()
        handleToolbar()

        getProductDetails()
    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private fun handleToolbar() {
        binding.includedToolbar.title.text = "Product Details"
        binding.includedToolbar.backBtn.setOnClickListener { finish() }
    }


    private fun clickListener() {

        binding.btnAddCart.setOnClickListener {

            if (productId == 0 || price == 0) {
                toast("Product Price is not available")
                return@setOnClickListener
            }
            addProductToCart()
        }

        binding.btnCart.setOnClickListener {
            openActivity(CartActivity::class.java)
        }


        binding.btnViewAR.setOnClickListener {


            if (assetUrl.isEmpty()) {
                toast("Asset is not Found")
                return@setOnClickListener
            }

            val intent = Intent(context, ARactivity::class.java).also {
                it.putExtra("asset", imageUrl+assetUrl)
            }
            startActivity(intent)
        }
    }


    private fun getProductDetails() {

        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.getProductDetails(productId)


                handleRestaurantDetails(response.product)

            } catch (exception: Exception) {

                toast(exception.message.toString())
            }
        }
    }


    private fun handleRestaurantDetails(product: Product?) {

        product?.let {
            binding.apply {
                tvRestaurantName.text = product.name
                tvDesc.text = product.desc

                price = product.price

                assetUrl = product.model ?: ""

                Glide.with(context)
                        .load(Config.imageUrl + product.image)
                        .into(restaurantImage)


                binding.btnViewAR.isVisible = product.isAr == 1


            }
        }
    }


    /*--------------------------------- Add To Cart --------------------------------*/

    private fun addProductToCart() {

        lifecycleScope.launch {
            try {

                val apiInterface: ApiService = RetroClass.createService(ApiService::class.java)
                val response = apiInterface.addToCart(
                        productId = productId,
                        userId = Config.user_id,
                )

                toast(response.message!!)

            } catch (exception: Exception) {

                toast(exception.message.toString())
            }
        }
    }

}