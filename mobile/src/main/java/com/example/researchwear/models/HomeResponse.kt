package com.example.researchwear.models


import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("homeList")
    val homeList: List<Home>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)