package com.mar_iguana.tours.ui.home.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.mar_iguana.tours.databinding.FragmentStepOneBinding


class StepOneFragment : Fragment() {
    private var _binding : FragmentStepOneBinding? = null
    private val b get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepOneBinding.inflate(inflater, container, false)
        val view = b.root

        val seats = arrayOf(1, 2, 3, 4, 5)
        val seatsAdapter = context?.let { ArrayAdapter<Int>(it, android.R.layout.simple_spinner_item, seats) }
        b.spinnerBookSeats.adapter = seatsAdapter
        b.spinnerBookSeats.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view1: View?, position: Int, id: Long) {
                seats[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return view
    }

}