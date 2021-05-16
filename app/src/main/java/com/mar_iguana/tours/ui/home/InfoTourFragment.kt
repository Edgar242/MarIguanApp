package com.mar_iguana.tours.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.ActivityMainBinding
import com.mar_iguana.tours.models.Tour
import java.text.NumberFormat
import java.util.*

class InfoTourFragment : Fragment() {

    private lateinit var info: Tour

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info_tour, container, false)

        // Has Options menu
        setHasOptionsMenu(true)

        //get info tour
        val tourInfo =  getArguments()?.getParcelable<Tour>("dataTour")
        if (tourInfo != null) {
            info = tourInfo
        }

        val title = view.findViewById<TextView>(R.id.titleTextView)
        val dates = view.findViewById<TextView>(R.id.datesTextView)
        val price = view.findViewById<TextView>(R.id.priceTextView)
        val detail = view.findViewById<TextView>(R.id.detailTextView)
        val ratingTour = view.findViewById<RatingBar>(R.id.tourRatingBar)
        val promo = view.findViewById<TextView>(R.id.promoTextView)

        title.text = tourInfo?.title
        val period = tourInfo?.dates!![0] +" - " + tourInfo?.dates!![1]
        dates.text = period
        price.text = tourInfo?.price?.let { formatPrice(it) }
        detail.text = tourInfo?.info
        ratingTour.rating = tourInfo!!.rating
        promo.text = tourInfo.promo

        return view
    }

    fun formatPrice(number: Float) : String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("MXN")
        return format.format(number)
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
                    putExtra(Intent.EXTRA_TEXT, info.urlInfoWeb)
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