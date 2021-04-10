package com.mar_iguana.tours.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.PagerTabAdapter
import com.mar_iguana.tours.databinding.FragmentTourDetailBinding
import com.mar_iguana.tours.models.Tour


class TourDetailFragment : Fragment() {

    lateinit var imageViewPager : ViewPager2
    private var _binding: FragmentTourDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get data Tour
        val tourDetail =  arguments?.getParcelable<Tour>("dataTour")

        tourDetail?.images?.get(0)?.let { binding.appBarImage.setImageResource(it) }

        initViewPager(view, tourDetail)
        return view
    }

    //Configure tabLayout and ViewPager to show detail
    private fun initViewPager(view:View, tour: Tour?){
        val adapterPager = PagerTabAdapter(parentFragmentManager , lifecycle)
        tour?.let { adapterPager.setTour(it) }
        binding.pagerDetailTour.adapter = adapterPager

        val tabTitles: ArrayList<String> = arrayListOf(getString(R.string.tab_info), getString(R.string.tab_itinerary))
        TabLayoutMediator(binding.tabInfoTour, binding.pagerDetailTour){
            tab, position -> tab.text = tabTitles[position]
        }.attach()
    }
}