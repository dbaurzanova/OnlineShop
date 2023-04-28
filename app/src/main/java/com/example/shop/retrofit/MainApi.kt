package com.example.shop.retrofit

import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {
    @GET("product/1")
    suspend  fun getProductById(): Product

    @GET("products")
    suspend fun getAllProducts() : Products
}