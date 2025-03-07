package com.example.productshopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.productshopping.apiuses.ApiService
import com.example.productshopping.apiuses.ServiceBuilder
import com.example.productshopping.dataclasses.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val _productsLiveData = MutableLiveData<Products?>()

    fun fetchProducts(): LiveData<Products?> {
        val apiService = ServiceBuilder.createService(ApiService::class.java)

        apiService.getProduct().enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful && response.body() != null) {
                    _productsLiveData.value = response.body()
                } else {
                    _productsLiveData.value = null
                }
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                _productsLiveData.value = null
            }
        })

        return _productsLiveData
    }
}
