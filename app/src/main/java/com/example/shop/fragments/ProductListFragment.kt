package com.example.shop.fragments

import ItemInfoFragment
import com.example.shop.adapter.ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.DataModel
import com.example.shop.ModelPreferencesManager
import com.example.shop.R
import com.example.shop.databinding.ListFragmentBinding
import com.example.shop.databinding.ListItemBinding
import com.example.shop.retrofit.MainApi
import com.example.shop.retrofit.Product
import com.example.shop.retrofit.Products
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ProductListFragment : Fragment(), ProductAdapter.Listener {

    private lateinit var adapter: ProductAdapter
    private lateinit var binding: ListFragmentBinding
    private val dataModel: DataModel by activityViewModels()
    private lateinit var list: Products

    lateinit var allProducts: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(layoutInflater)

        ModelPreferencesManager.withFragment(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductAdapter(this)

        val rcView: RecyclerView = requireView().findViewById(R.id.rc_view)
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val mainApi = retrofit.create(MainApi::class.java)

        if (ModelPreferencesManager.get<Products>("KEY_PRODUCTS")?.products == null) {
            CoroutineScope(Dispatchers.IO).launch {
                list = mainApi.getAllProducts()
                requireActivity().runOnUiThread {
                    binding.apply {
                        adapter.submitList(list.products)
                    }
                    dataModel.products.value = list.products
                }
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                requireActivity().runOnUiThread {
                    if(dataModel.products.value == null) {
                        dataModel.products.value =
                            ModelPreferencesManager.get<Products>("KEY_PRODUCTS")?.products
                    }
                    binding.apply {
                        adapter.submitList(dataModel.products.value)
                    }
                }
            }
        }

        allProducts = emptyList<Product>().toMutableList()
        dataModel.products.observe(activity as LifecycleOwner) {
            for (i in it.indices) {
                allProducts.add(it[i])
            }
            val productsList = Products(allProducts)
            ModelPreferencesManager.put(productsList, "KEY_PRODUCTS")
        }

    }

    override fun onClickItem(product: Product) {
        val fragmentB = ItemInfoFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.flFragment, fragmentB)
            ?.commit()

        // Transferring data about product from ProductList to ItemInfo
        dataModel.product.value = product
    }
}



