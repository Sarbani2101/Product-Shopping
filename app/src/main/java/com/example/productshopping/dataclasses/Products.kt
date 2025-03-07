package com.example.productshopping.dataclasses

data class Products(
    val products: List<Product>
)
data class Product(
    val brand: String ,
    val category: String ,
    val createdAt: String,
    val description: String,
    val image: String,
    val id: Int,
    val name: String,
    val price: String,
    val ratings: String,
    val stock: Int,
    val user: Int
)
