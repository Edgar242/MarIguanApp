package com.mar_iguana.tours.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mar_iguana.tours.MainActivity
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.ImageSliderAdapter
import com.mar_iguana.tours.adapters.PagerTabAdapter
import com.mar_iguana.tours.databinding.FragmentTourDetailBinding
import com.mar_iguana.tours.models.Tour
import com.mar_iguana.tours.ui.home.booking.BookFragment
import me.relex.circleindicator.CircleIndicator3


class TourDetailFragment : Fragment() {
    private var _binding: FragmentTourDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get data Tour
        val tourDetail =  getArguments()?.getParcelable<Tour>("dataTour")
        
        binding.buttonBook.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, BookFragment.newInstance(tourDetail!!))
                addToBackStack(null)
                commit()
            }
        }

        //Begin Image Slider Bar
        val viewPagerImages: ViewPager2 = view.findViewById(R.id.viewPagerBar)
        viewPagerImages.adapter = tourDetail?.images?.let { ImageSliderAdapter(it) }
        viewPagerImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator: CircleIndicator3 = view.findViewById(R.id.indicator)
        indicator.setViewPager(viewPagerImages)
        viewPagerImages.adapter?.registerAdapterDataObserver(indicator.adapterDataObserver)
        //End Image Slider Bar

        initViewPager(view, tourDetail)

        // Hide bottom navigation bar in non top level fragments
        (activity as MainActivity).binding.navView.visibility = View.GONE

        return view
    }

    //Configure tabLayout and ViewPager to show detail
    private fun initViewPager(view:View, tour: Tour?){
        val viewPager: ViewPager2 = view.findViewById(R.id.pagerDetailTour)
        val adapterPager = PagerTabAdapter(childFragmentManager , lifecycle)
        tour?.let { adapterPager.setTour(it) }
        viewPager.adapter = adapterPager

        val tabLayoutInfoTour:TabLayout = view.findViewById(R.id.tabInfoTour)
        val tabTitles: ArrayList<String> = arrayListOf(getString(R.string.tab_info), getString(R.string.tab_itinerary))
        TabLayoutMediator(tabLayoutInfoTour, viewPager){
            tab, position -> tab.text = tabTitles[position]
        }.attach()
    }

}