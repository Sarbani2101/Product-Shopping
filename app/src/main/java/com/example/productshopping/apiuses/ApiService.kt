package com.example.productshopping.apiuses

import com.example.productshopping.dataclasses.LoginData
import com.example.productshopping.dataclasses.Products
import com.example.productshopping.dataclasses.RegisterData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("django-eshop/api/products/")
    fun getProduct(): Call<Products>

    @FormUrlEncoded
    @POST("django-eshop/api/auth/user/register/")
    fun registerUser(
        @Field("full_name") fullName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ): Call<RegisterData>

    @FormUrlEncoded
    @POST("django-eshop/api/auth/user/login/")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginData>
}