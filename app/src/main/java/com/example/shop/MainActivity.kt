package com.example.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.fragments.FavouriteFragment
import com.example.shop.fragments.HomeFragment
import com.example.shop.fragments.ProductListFragment
import com.example.shop.retrofit.Product

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFr = HomeFragment()
        val listFr = ProductListFragment()
        val favFr = FavouriteFragment()

        setCurrentFragment(homeFr)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(homeFr)
                R.id.list_of_product -> setCurrentFragment(listFr)
                R.id.favourites -> setCurrentFragment(favFr)
            }
            true
        }

     }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

}