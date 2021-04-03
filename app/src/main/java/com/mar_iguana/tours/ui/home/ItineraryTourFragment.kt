package com.mar_iguana.tours.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mar_iguana.tours.R
import com.mar_iguana.tours.models.Tour

class ItineraryTourFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_itinerary_tour, container, false)
        val tourInfo =  getArguments()?.getParcelable<Tour>("dataTour")

        val itinerary = view.findViewById<TextView>(R.id.itineraryTextView)
        itinerary.text = tourInfo?.itinerary
        return view
    }

}