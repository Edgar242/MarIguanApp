package com.mar_iguana.tours.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.PagerTabAdapter
import com.mar_iguana.tours.databinding.FragmentTourDetailBinding
import com.mar_iguana.tours.models.Tour
import com.mar_iguana.tours.ui.home.book.BookFragment

class TourDetailFragment : Fragment() {

    private var _binding: FragmentTourDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get data Tour
        val tourDetail =  arguments?.getParcelable<Tour>("dataTour")
        initViewPager()
        
        binding.buttonBook.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, BookFragment.newInstance(tourDetail!!))
                commit()
            }
        }

        return view
    }

    //Configure tabLayout and ViewPager to show detail
    private fun initViewPager(){
        //Get data Tour
        val tourDetail =  arguments?.getParcelable<Tour>("dataTour")
        tourDetail?.images?.get(0)?.let { binding.appBarImage.setImageResource(it) }

        val adapterPager = PagerTabAdapter(parentFragmentManager , lifecycle)
        tourDetail?.let { adapterPager.setTour(it) }
        binding.pagerDetailTour.adapter = adapterPager

        val tabTitles: ArrayList<String> = arrayListOf(getString(R.string.tab_info), getString(R.string.tab_itinerary))
        TabLayoutMediator(binding.tabInfoTour, binding.pagerDetailTour){
            tab, position -> tab.text = tabTitles[position]
        }.attach()
    }
}