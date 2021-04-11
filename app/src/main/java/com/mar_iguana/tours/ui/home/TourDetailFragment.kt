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
import com.mar_iguana.tours.adapters.ImageSliderAdapter
import com.mar_iguana.tours.adapters.PagerTabAdapter
import com.mar_iguana.tours.models.Tour
import me.relex.circleindicator.CircleIndicator3


class TourDetailFragment : Fragment() {

    lateinit var imageViewPager : ViewPager2
    //private var imagesBarList = mutableListOf<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tour_detail, container, false)

        //Get data Tour
        val tourDetail =  getArguments()?.getParcelable<Tour>("dataTour")

        //Begin Image Slider Bar
        val viewPagerImages: ViewPager2 = view.findViewById(R.id.viewPagerBar)
        viewPagerImages.adapter = tourDetail?.images?.let { ImageSliderAdapter(it) }
        viewPagerImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator: CircleIndicator3 = view.findViewById(R.id.indicator)
        indicator.setViewPager(viewPagerImages)
        viewPagerImages.adapter?.registerAdapterDataObserver(indicator.adapterDataObserver);
        //End Image Slider Bar

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