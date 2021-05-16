package com.mar_iguana.tours.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mar_iguana.tours.ui.home.booking.BookStepOneFragment
import com.mar_iguana.tours.ui.home.booking.BookStepThreeFragment
import com.mar_iguana.tours.ui.home.booking.BookStepTwoFragment

class ViewPagerStepperAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val bookStepFragments = arrayListOf(
        BookStepOneFragment(),
        BookStepTwoFragment(),
        BookStepThreeFragment()
    )

    override fun getItemCount(): Int {
        return bookStepFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return bookStepFragments[position]
    }
}