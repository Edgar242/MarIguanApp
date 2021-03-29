package com.mar_iguana.tours.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.TourAdapter
import com.mar_iguana.tours.databinding.FragmentHomeBinding
import com.mar_iguana.tours.models.Tour

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    // _binding property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tourAdapter: TourAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = it
        })

        showTours()
        return view
    }

    private fun showTours() {
        val products = arrayListOf<Tour>(
            Tour(
                100,
                "Real de Catorce y la Media Luna (Fin de semana)",
                2150F,
                "Del 14 al 16 de mayo de 2021",
                4.8F,
                listOf<Int>(R.drawable.real_de_catorce),
            ),
            Tour(
                100,
                "Real de Catorce y la Media Luna (Fin de semana)",
                2150F,
                "Del 14 al 16 de mayo de 2021",
                4.8F,
                listOf<Int>(R.drawable.real_de_catorce),
            ),
            Tour(
                100,
                "Real de Catorce y la Media Luna (Fin de semana)",
                2150F,
                "Del 14 al 16 de mayo de 2021",
                4.8F,
                listOf<Int>(R.drawable.real_de_catorce),
            ),
            Tour(
                100,
                "Real de Catorce y la Media Luna (Fin de semana)",
                2150F,
                "Del 14 al 16 de mayo de 2021",
                4.8F,
                listOf<Int>(R.drawable.real_de_catorce),
            ),
        )

        // Set adapter
        binding.recyclerViewTours.adapter = TourAdapter(products)
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        binding.recyclerViewTours.layoutManager = layoutManager
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}