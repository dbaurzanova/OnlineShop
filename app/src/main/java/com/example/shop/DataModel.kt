package com.example.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop.retrofit.Product
import com.example.shop.retrofit.Products

class DataModel: ViewModel() {
    val product : MutableLiveData<Product> by lazy {
        MutableLiveData<Product>()
    }

    val products : MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }
}