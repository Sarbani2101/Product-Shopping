package com.example.productshopping.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.productshopping.R


class SpinnerAdapter(context: Context, private val items: List<String>) : ArrayAdapter<String>(context, R.layout.spinner_item, items) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinner_text)
        textView.text = items[position]


        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinner_dropdown_text)
        textView.text = items[position]

        return view
    }
}
