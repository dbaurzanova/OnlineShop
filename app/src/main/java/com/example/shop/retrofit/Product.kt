package com.example.shop.retrofit

import com.example.shop.fragments.FavouriteFragment

data class Product(
    val id : Int,
    val title : String,
    val description : String,
    val price : Int,
    val discountPercentage : Float,
    val rating : Float,
    val stock : Int,
    val brand : String,
    val category : String,
    val thumbnail : String,
    val images : List<String>,
    var isFavourite: Boolean = false
)
