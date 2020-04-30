package com.example.researchwear.models

import com.google.gson.annotations.SerializedName

data class StoreResponse
    (
    @SerializedName("locations") val allStores: List<AllStores>,
    @SerializedName("status") val status:Boolean

)