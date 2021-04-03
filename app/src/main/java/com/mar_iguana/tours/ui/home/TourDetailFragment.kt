package com.mar_iguana.tours.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.PagerTabAdapter
import com.mar_iguana.tours.models.Tour


class TourDetailFragment : Fragment() {

    lateinit var imageViewPager : ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tour_detail, container, false)

        val barImage = view.findViewById<ImageView>(R.id.app_bar_image)

        //Get data Tour
        val tourDetail =  getArguments()?.getParcelable<Tour>("dataTour")

        tourDetail?.images?.get(0)?.let { barImage.setImageResource(it) }

        initViewPager(view, tourDetail)
        return view
    }

    //Configure tabLayout and ViewPager to show detail
    private fun initViewPager(view:View, tour: Tour?){
        val viewPager: ViewPager2 = view.findViewById(R.id.pagerDetailTour)
        val adapterPager = PagerTabAdapter(parentFragmentManager , lifecycle)
        tour?.let { adapterPager.setTour(it) }
        viewPager.adapter = adapterPager

        val tabLayoutInfoTour:TabLayout = view.findViewById(R.id.tabInfoTour)
        val tabTitles: ArrayList<String> = arrayListOf(getString(R.string.tab_info), getString(R.string.tab_itinerary))
        TabLayoutMediator(tabLayoutInfoTour, viewPager){
            tab, position -> tab.text = tabTitles[position]
        }.attach()
    }
}