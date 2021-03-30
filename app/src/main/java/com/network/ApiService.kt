package com.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    companion object {
        const val apiUrl = "Api.php?apicall="
    }


    /*------------------------------------ Product List ------------------------------------*/

    @GET(apiUrl + "get_product_list")
    suspend fun getProductList(): ServerResponse


    /*------------------------------------ Product Details ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "get_product_details")
    suspend fun getProductDetails(
            @Field("product_id") productId: Int,
    ): ServerResponse


    /*------------------------------------ Add To Cart ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "add_cart")
    suspend fun addToCart(
            @Field("product_id") productId: Int,
            @Field("u_id") userId: Int,
     ): ServerResponse


    /*------------------------------------ Remove To Cart ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "remove_cart")
    suspend fun removeToCart(
            @Field("cart_id") cartId: Int,
    ): ServerResponse


    /*------------------------------------ Update Cart ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "updatecart")
    suspend fun updateToCart(
            @Field("cart_id") cartId: Int,
            @Field("qty") qty: Int,
    ): ServerResponse


    /*------------------------------------ Get Cart Data ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "get_cart_data")
    suspend fun getCartData(
            @Field("u_id") userId: Int,
    ): ServerResponse


    /*------------------------------------ Get Grand Total ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "get_grand_total")
    suspend fun getGrandTotal(
            @Field("u_id") userId: Int,
    ): ServerResponse


    /*------------------------------------ Place Order ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "order")
    suspend fun placeOrder(
            @Field("u_id") userId: Int,
            @Field("address") address: String,
    ): ServerResponse


    /*------------------------------------ Order History ------------------------------------*/

    @FormUrlEncoded
    @POST(apiUrl + "order_history")
    suspend fun getOrderHistory(
            @Field("u_id") userId: Int,
    ): ServerResponse


}