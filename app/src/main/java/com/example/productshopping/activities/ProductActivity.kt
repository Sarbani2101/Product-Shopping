package com.example.productshopping.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productshopping.adapter.ProductAdapter
import com.example.productshopping.R
import com.example.productshopping.viewmodel.ProductViewModel
import com.example.productshopping.adapter.SpinnerAdapter

class ProductActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner: Spinner
    private lateinit var searchField: EditText
    private lateinit var tipsTextView: TextView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var spinnerAdapter : SpinnerAdapter

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        initViews()
        setupRecyclerView()
        setupObservers()
        setupSearchField()
        setupSpinner()
        productViewModel.fetchProducts()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view_products)
        progressBar = findViewById(R.id.progressBar)
        spinner = findViewById(R.id.spinner_category)
        searchField = findViewById(R.id.search_field)
        tipsTextView = findViewById(R.id.tipsTV)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(emptyList(), this)
        recyclerView.adapter = productAdapter
    }

    private fun setupObservers() {
        productViewModel.filteredProducts.observe(this, Observer { products ->
            productAdapter.updateList(products)
            tipsTextView.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
            progressBar.visibility = if (products.isNotEmpty()) View.GONE else View.VISIBLE
        })

        productViewModel.categories.observe(this, Observer { categories ->
            setupSpinnerAdapter(categories)
        })
    }

    private fun setupSearchField() {
        searchField.setText(productViewModel.searchQuery)
        searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSpinner() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterProducts()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterProducts() {
        val selectedCategory = spinner.selectedItem?.toString() ?: "All"
        val query = searchField.text.toString()
        productViewModel.filterProducts(selectedCategory, query)
    }

    private fun setupSpinnerAdapter(categories: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}
