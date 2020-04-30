package com.example.researchwear.models


import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)