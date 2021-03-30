package com.data


import com.google.gson.annotations.SerializedName

data class Product(
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

        @SerializedName("price")
        val price: Int,

        @SerializedName("product_id")
        val productId: String,
)