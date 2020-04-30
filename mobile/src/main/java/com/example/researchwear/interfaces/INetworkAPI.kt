package com.example.researchwear.interfaces

import com.example.researchwear.models.HomeResponse
import com.example.researchwear.models.StoreResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET


interface INetworkAPI {

    @GET("getNearestAPI")
    fun getAllPosts(): Observable<Response<StoreResponse>>

    @GET("homeAPI")
    fun getHomeAPI(): Observable<Response<HomeResponse>>
    //fun getHomeImg():Observable<Response<HomeResponse>>
}