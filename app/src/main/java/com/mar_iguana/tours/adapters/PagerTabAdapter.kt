package com.mar_iguana.tours.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mar_iguana.tours.models.Tour
import com.mar_iguana.tours.ui.home.InfoTourFragment
import com.mar_iguana.tours.ui.home.ItineraryTourFragment

class PagerTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private lateinit var tour: Tour

    var fragments : ArrayList<Fragment> = arrayListOf(
        InfoTourFragment(),
        ItineraryTourFragment()
    )
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        val bundleTour = Bundle()
        bundleTour.putParcelable("dataTour", tour)
        fragments[position].arguments = bundleTour
        return fragments[position]
    }

    fun setTour(tour:Tour){
        this.tour = tour
    }
}