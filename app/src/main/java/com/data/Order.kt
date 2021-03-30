package com.data


import com.google.gson.annotations.SerializedName

data class Order(
        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("product_id")
        val productId: Int,

        @SerializedName("product_img")
        val productImg: String,

        @SerializedName("address")
        val address: String,

        @SerializedName("product_name")
        val productName: String,

        @SerializedName("qty")
        val qty: Int,

        @SerializedName("total")
        val total: Int,
)