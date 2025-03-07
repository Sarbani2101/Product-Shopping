package com.example.productshopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productshopping.dataclasses.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = Repository()
    private val _filteredProducts = MutableLiveData<List<Product>>()
    val filteredProducts: LiveData<List<Product>> = _filteredProducts

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private var allProducts: List<Product> = emptyList()

    private var selectedCategory: String = "All"
    var searchQuery: String = ""

    fun fetchProducts() {
        viewModelScope.launch {
            repository.fetchProducts().observeForever { products ->
                products?.let {
                    allProducts = it.products
                    updateCategories()
                    filterProducts(selectedCategory, searchQuery)
                }
            }
        }
    }

    private fun updateCategories() {
        val categoryList = listOf("All") + allProducts.map { it.category }.distinct()
        _categories.value = categoryList
    }

    fun filterProducts(category: String, query: String) {
        selectedCategory = category
        searchQuery = query

        _filteredProducts.value = allProducts.filter { product ->
            (category == "All" || product.category.equals(category, ignoreCase = true)) &&
                    product.name.contains(query, ignoreCase = true)
        }
    }
}
