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
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.databinding.FavouriteFragmentBinding
import com.example.shop.databinding.ListItemBinding
import com.example.shop.retrofit.Product
import com.example.shop.retrofit.Products

open class FavouriteFragment : Fragment(), ProductAdapter.Listener {

    private lateinit var adapter : ProductAdapter
    private lateinit var binding : FavouriteFragmentBinding
    private val dataModel : DataModel by activityViewModels()

    lateinit var productsFav : MutableList<Product>
    lateinit var allProducts : MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouriteFragmentBinding.inflate(layoutInflater)

        ModelPreferencesManager.withFavFragment(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductAdapter(this)

        val rcView : RecyclerView = requireView().findViewById<RecyclerView>(R.id.rc_view_fav)
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter

        productsFav = emptyList<Product>().toMutableList()

        if(ModelPreferencesManager.get<Products>("KEY_PRODUCTS")?.products != null) {
            if(dataModel.products.value == null){
                dataModel.products.value =
                    ModelPreferencesManager.get<Products>("KEY_PRODUCTS")?.products
            }
            dataModel.products.observe(activity as LifecycleOwner) {
                for (i in it.indices) {
                    if (it[i].isFavourite) {
                        productsFav.add(it[i])
                    }
                }
            }
            binding.rcViewFav.visibility = View.VISIBLE
            binding.favText.visibility = View.GONE
            binding.apply {
                adapter.submitList(productsFav)
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