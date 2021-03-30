package com.network

import com.data.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("error")
    @Expose
    var error: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("product")
    @Expose
    var product: Product? = null

    @SerializedName("products")
    @Expose
    var products: List<Product>? = null


    @SerializedName("carts")
    @Expose
    var carts: List<Cart>? = null

    @SerializedName("grand_total")
    @Expose
    var grandTotal: GrandTotal? = null


    @SerializedName("orders")
    @Expose
    var orders: List<Order>? = null

    
}