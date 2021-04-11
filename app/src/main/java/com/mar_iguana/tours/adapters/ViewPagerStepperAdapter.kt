package com.mar_iguana.tours.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mar_iguana.tours.ui.home.book.StepOneFragment
import com.mar_iguana.tours.ui.home.book.StepThreeFragment
import com.mar_iguana.tours.ui.home.book.StepTwoFragment

class ViewPagerStepperAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var stepFragments = arrayListOf(
        StepOneFragment(),
        StepTwoFragment(),
        StepThreeFragment()
    )

    override fun getItemCount(): Int {
        return stepFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return stepFragments[position]
    }
}