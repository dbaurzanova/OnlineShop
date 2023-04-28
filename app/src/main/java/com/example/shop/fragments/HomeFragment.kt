package com.example.shop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.shop.R
import com.example.shop.adapter.ViewPagerAdapter


open class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.home_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = listOf(
            R.drawable.decor,
            R.drawable.romantic,
            R.drawable.food
        )
        val adapter = ViewPagerAdapter(images)

        getView()?.findViewById<ViewPager2>(R.id.viewPager)?.adapter = adapter
//        getView()?.findViewById<ViewPager2>(com.example.shop.R.id.viewPager)?.orientation = ViewPager2.ORIENTATION_VERTICAL



    }

}