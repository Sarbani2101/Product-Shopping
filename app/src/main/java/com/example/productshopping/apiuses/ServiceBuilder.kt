package com.example.productshopping.apiuses

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "https://swarupapp.in/"
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()).client(client).build()
    fun <T> createService(service : Class<T>) :T{
        return retrofit.create(service)
    }
}