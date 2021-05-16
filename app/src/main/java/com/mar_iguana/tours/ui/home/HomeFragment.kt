package com.mar_iguana.tours.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.TourAdapter
import com.mar_iguana.tours.databinding.FragmentHomeBinding
import com.mar_iguana.tours.listeners.TourListener
import com.mar_iguana.tours.models.Tour
import com.mar_iguana.tours.response.TourResponse
import com.mar_iguana.tours.webservices.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), TourListener {

    // _binding property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tourAdapter: TourAdapter
    private val lstTours = ArrayList<Tour>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        //Init recycler view
        initRecyclerView()
        //Get tours by call to webService
        showTours()
        return view
    }

    private fun initRecyclerView(){
        tourAdapter = TourAdapter(lstTours)
        tourAdapter.setTourListener(this)
        // Set adapter
        binding.recyclerViewTours.adapter = tourAdapter
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        binding.recyclerViewTours.layoutManager = layoutManager
    }

    private fun showTours() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<TourResponse> = getRetrofit().create(APIService::class.java).getTours("tours")
            val responseTours: TourResponse? = call.body()

            //run in main thread
            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    val tours: ArrayList<Tour> = responseTours?.tours ?: ArrayList<Tour>()
                    Log.i("Size tours", tours.size.toString())
                    lstTours.clear()
                    lstTours.addAll(tours)
                    tourAdapter.notifyDataSetChanged()
                } else {
                   Log.e("Error 0", "error en llamada")
                }
            }
        }
    }

    //Client
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://demo7188737.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Actions to do when click over cardView
    override fun onClickShowTourDetail(tour: Tour) {
        val tourDetailFragment = TourDetailFragment()

        //Set tour data to detail fragment
        val bundleTour = Bundle()
        bundleTour.putParcelable("dataTour",tour)
        tourDetailFragment.arguments = bundleTour

        //Go to detail fragment
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, tourDetailFragment)
            addToBackStack(null)
            commit()
        }
    }

}