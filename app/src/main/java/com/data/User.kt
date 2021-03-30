package com.data

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("u_email")
        val uEmail: String,
        @SerializedName("u_id")
        val uId: Int,
        @SerializedName("u_mob")
        val uMob: String,
        @SerializedName("u_name")
        val uName: String,
        @SerializedName("u_password")
        val uPassword: String,
)