package com.data


import com.google.gson.annotations.SerializedName

data class GrandTotal(
    @SerializedName("total")
    val total: Int
)