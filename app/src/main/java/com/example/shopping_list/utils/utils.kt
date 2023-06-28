package com.example.shopping_list.utils

import android.util.Log
import com.example.shopping_list.entity.Article
import com.example.shopping_list.entity.Product

fun createDoubleListProduct(products: List<Product>): List<List<Product>>{
    val doubleList = mutableListOf<List<Product>>()
    val listProduct = mutableListOf<Product>()
    if (products.isNotEmpty()) {
        var currentSection = products[0].article.section.idSection
        products.forEach { item->
            if (currentSection == item.article.section.idSection) listProduct.add(item)
            else {
                doubleList.add(listProduct.toList())
                currentSection = item.article.section.idSection
                listProduct.clear()
                listProduct.add(item)
            }
        }
        if (listProduct.isNotEmpty()) doubleList.add(listProduct)
    }
    return doubleList
}
fun createDoubleLisArticle(articles: List<Article>): List<List<Article>>{
    val doubleList = mutableListOf<List<Article>>()
    val listArticle = mutableListOf<Article>()
    if (articles.isNotEmpty()) {
        var currentSection = articles[0].section.idSection
        articles.forEach { item->
            if (currentSection == item.section.idSection) listArticle.add(item)
            else {
                doubleList.add(listArticle.toList())
                currentSection = item.section.idSection
                listArticle.clear()
                listArticle.add(item)
            }
        }
        if (listArticle.isNotEmpty()) doubleList.add(listArticle)
    }
    return doubleList
}

fun log(text: String){
    Log.d("KDS", text)
}