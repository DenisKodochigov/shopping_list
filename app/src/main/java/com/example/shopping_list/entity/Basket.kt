package com.example.shopping_list.entity

interface Basket {
    var idBasket: Long
    var nameBasket: String
    var fillBasket: Boolean
    var quantity: Int
    var isSelected:Boolean
}