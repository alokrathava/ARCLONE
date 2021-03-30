package com.data


import com.google.gson.annotations.SerializedName

data class Cart(
        @SerializedName("cart_id")
        val cartId: Int,

        @SerializedName("desc")
        val desc: String,

        @SerializedName("image")
        val image: String?,

        @SerializedName("is_ar")
        val isAr: Int,

        @SerializedName("model")
        val model: String?,

        @SerializedName("name")
        val name: String,

        @SerializedName("product_price")
        val productPrice: Int,

        @SerializedName("qty")
        val qty: Int = 1,
)