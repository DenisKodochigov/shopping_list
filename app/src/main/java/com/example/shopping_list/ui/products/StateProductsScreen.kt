package com.example.shopping_list.ui.products

import com.example.shopping_list.entity.Product

data class StateProductsScreen(
    val products: List<Product> = emptyList(),
)
