package com.mar_iguana.tours.ui.home.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.FragmentBookStepTwoBinding
import com.mar_iguana.tours.utils.Utils

const val TITLE = 0
const val PRICE = 1

class BookStepTwoFragment : Fragment() {
    private var _binding : FragmentBookStepTwoBinding? = null
    private val b get() = _binding!!
    private lateinit var bookFragment: BookFragment
    private val extras = mutableListOf<Int>()
    private var optionSelected = 0
    private var promo = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookStepTwoBinding.inflate(inflater, container, false)
        val view = b.root

        bookFragment = parentFragment as BookFragment
        val total = bookFragment.seatCounter * (bookFragment.tourDetail.price)
        loadRoomOptions()

        // Text displayed
        b.textViewDeparture.text = bookFragment.tourDetail.dates
        b.textViewDestiny.text = bookFragment.tourDetail.title
        b.checkBoxPromotion.text = "Promo:\n" + bookFragment.tourDetail.promo!!.split("|")[0]
        b.textViewTotal.text = getString(R.string.total_step_two, "${bookFragment.seatCounter} x ${bookFragment.tourDetail.price} = ${Utils.localCurrencyFormat(total)}")

        // Checkbox promo
        b.checkBoxPromotion.setOnCheckedChangeListener { buttonView, isChecked ->
            promo = if (isChecked) bookFragment.tourDetail.promo!!.split("|")[1].toInt() else 0
            updateTotals(optionSelected)
        }

        // Radio button options
        b.radioGroupRoomSize.setOnCheckedChangeListener { _, option ->
            updateTotals(option)
        }

        return view
    }

    private fun updateTotals(option : Int) {
        optionSelected = option
        val total = bookFragment.seatCounter * (bookFragment.tourDetail.price + extras[option] + promo)
        val seatPriceExtra = if (extras[option] == 0) "${bookFragment.tourDetail.price + promo}" else "(${bookFragment.tourDetail.price + promo}+${extras[option]})"
        b.textViewTotal.text = getString(R.string.total_step_two, "${bookFragment.seatCounter} x $seatPriceExtra = ${Utils.localCurrencyFormat(total)}")
    }

    override fun onResume() {
        super.onResume()
        updateTotals(optionSelected)
    }

    private fun loadRoomOptions() {
        val roomOptions = bookFragment.tourDetail.roomOptions

        // Set room description and price
        for (room in roomOptions){
            val parts = room.split("|")
            val price =  Utils.localCurrencyFormat(parts[PRICE].toInt())
            val description = "${parts[TITLE]} + $price"
            extras.add(parts[PRICE].toInt())

            // Radio button setting
            RadioButton(context).apply {
                isChecked = roomOptions.indexOf(room) == 0
                id = roomOptions.indexOf(room)
                text = description
                b.radioGroupRoomSize.addView(this)
            }
        }
    }
}