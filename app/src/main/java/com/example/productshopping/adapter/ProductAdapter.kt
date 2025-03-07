package com.example.productshopping.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productshopping.activities.ProductDetailsActivity
import com.example.productshopping.dataclasses.Product
import com.example.productshopping.R

class ProductAdapter(private var productList: List<Product>, private val context: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.product_image)
        private val brandTV: TextView = view.findViewById(R.id.text_product_brand_category)
        private val priceTV: TextView = view.findViewById(R.id.text_product_price_stock)
        private val ratingsTV: TextView = view.findViewById(R.id.text_product_ratings)
        private val createdAtTV: TextView = view.findViewById(R.id.text_product_created_at)
        private val titleTV: TextView = view.findViewById(R.id.text_product_name)
        private val descriptionTV: TextView = view.findViewById(R.id.text_product_description)

        private var isExpanded = false

        @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            Glide.with(itemView.context)
                .load(item.image)
                .into(image)

            val brandT = item.brand
            val categoryT = item.category
            brandTV.text = "$brandT | $categoryT"

            val priceT = "Price: ₹${item.price}"
            val stockT = "Stock: ${item.stock }"
            priceTV.text = "$priceT | $stockT"

            ratingsTV.text = "Ratings: ${item.ratings }"
            createdAtTV.text = "Created At: ${item.createdAt }"
            titleTV.text = item.name

            val fullDescription = item.description
            setDescriptionText(fullDescription)

            descriptionTV.setOnClickListener {
                isExpanded = !isExpanded
                setDescriptionText(fullDescription)
            }
        }

        private fun setDescriptionText(fullDescription: String) {
            val words = fullDescription.split(" ")
            descriptionTV.text = if (isExpanded) {
                "$fullDescription...See less"
            } else {
                if (words.size > 8) {
                    val shortDescription = words.take(8).joinToString(" ")
                    "$shortDescription...Read more"
                } else {
                    fullDescription
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = productList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra("title", item.name)
                putExtra("description", item.description)
                putExtra("category", item.category)
                putExtra("rating", item.ratings)
                putExtra("stock", item.stock)
                putExtra("price", item.price)
                putExtra("brand", item.brand)
                putExtra("thumbnail", item.image)
                putExtra("createdAt", item.createdAt)
                putExtra("userId", item.user)
            }
            context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }

}
