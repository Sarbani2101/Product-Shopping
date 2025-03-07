package com.example.productshopping.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.productshopping.R

class ProductDetailsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Initialize views
        val productImage: ImageView = findViewById(R.id.productImage)
        val productName: TextView = findViewById(R.id.productName)
        val productBrand: TextView = findViewById(R.id.productBrand)
        val productCategory: TextView = findViewById(R.id.productCategory)
        val productPrice: TextView = findViewById(R.id.productPrice)
        val productRatings: TextView = findViewById(R.id.productRatings)
        val productStock: TextView = findViewById(R.id.productStock)
        val productUser: TextView = findViewById(R.id.productUser)
        val productCreatedAt: TextView = findViewById(R.id.productCreatedAt)
        val productDescription: TextView = findViewById(R.id.productDescription)

        // Retrieve data from Intent
        val title = intent.getStringExtra("title") ?: "N/A"
        val description = intent.getStringExtra("description") ?: "No description available."
        val category = intent.getStringExtra("category") ?: "Unknown Category"
        val rating = intent.getStringExtra("rating") ?: "No Ratings"
        val stock = intent.getIntExtra("stock", 0)
        val price = intent.getDoubleExtra("price", 0.0)
        val brand = intent.getStringExtra("brand") ?: "Unknown Brand"
        val image = intent.getStringExtra("thumbnail")
        val createdAt = intent.getStringExtra("createdAt")
        val userId = intent.getIntExtra("userId", 0)

        // Populate data into views
        productName.text = title
        productBrand.text = "Brand: $brand"
        productCategory.text = "Category: $category"
        productPrice.text = "Price: ₹$price"
        productRatings.text = "Ratings: $rating"
        productStock.text = "In Stock: $stock"
        productUser.text = "User ID: ${if (userId != -1) userId.toString() else "N/A"}"
        productCreatedAt.text = "Created At: $createdAt"
        productDescription.text = description

        // Load the image using Glide
        Glide.with(this)
            .load(image)// Add a placeholder image in case the URL is null
            .into(productImage)
    }
}
