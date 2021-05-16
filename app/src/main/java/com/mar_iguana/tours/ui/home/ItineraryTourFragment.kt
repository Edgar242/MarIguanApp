package com.mar_iguana.tours.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.mar_iguana.tours.R
import com.mar_iguana.tours.models.Tour

class ItineraryTourFragment : Fragment() {

    private lateinit var info: Tour

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_itinerary_tour, container, false)
        // Has Options menu
        setHasOptionsMenu(true)

        //get info tour
        val tourInfo =  getArguments()?.getParcelable<Tour>("dataTour")
        if (tourInfo != null) {
            info = tourInfo
        }

        val itinerary = view.findViewById<TextView>(R.id.itineraryTextView)

        val activities = tourInfo?.itinerary
        var stringActivities = ""
        if (activities != null) {
            for (activity in activities){
                stringActivities = stringActivities + activity + "\n\n"
            }
        }

        itinerary.text = stringActivities
        return view
    }

    //Set share option
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_button_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Set actions to share option
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.shareItem -> {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND_MULTIPLE
                    putExtra(Intent.EXTRA_TITLE, info.title)
                    putExtra(
                        Intent.EXTRA_TEXT, info.urlInfoWeb
                    )
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}