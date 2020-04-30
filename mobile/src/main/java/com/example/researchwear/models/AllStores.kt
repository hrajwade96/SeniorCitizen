package com.example.researchwear.models

import com.google.gson.annotations.SerializedName

data class AllStores(
    @SerializedName("store_name") val name: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("desc") val desc: String
)