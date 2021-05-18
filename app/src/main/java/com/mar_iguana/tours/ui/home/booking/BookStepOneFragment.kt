package com.mar_iguana.tours.ui.home.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mar_iguana.tours.R
import com.mar_iguana.tours.adapters.BookSeatAdapter
import com.mar_iguana.tours.databinding.FragmentBookStepOneBinding
import com.mar_iguana.tours.models.Seat

class BookStepOneFragment : Fragment() {
    private var _binding : FragmentBookStepOneBinding? = null
    private val b get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookStepOneBinding.inflate(inflater, container, false)
        val view = b.root

        setupRecyclerBookSeat()

        return view
    }

    private fun setupRecyclerBookSeat() {
        val seats = arrayListOf<Seat>(
                Seat(-1, 0), // Steering wheel
                Seat(0, 0),
                Seat(0, 0),
                Seat(0, 0),
                Seat(0, 0),

                Seat(1, 0),
                Seat(2, 2),
                Seat(0, 0),
                Seat(3, 2),
                Seat(4, 0),

                Seat(5, 0),
                Seat(6, 2),
                Seat(0, 0),
                Seat(7, 0),
                Seat(8, 0),

                Seat(9, 0),
                Seat(10, 0),
                Seat(0, 0),
                Seat(11, 0),
                Seat(12, 2),

                Seat(13, 2),
                Seat(14, 2),
                Seat(0, 0),
                Seat(15, 0),
                Seat(16, 0),

                Seat(17, 2),
                Seat(18, 0),
                Seat(0, 0),
                Seat(19, 0),
                Seat(20, 0),

                Seat(21, 0),
                Seat(22, 0),
                Seat(0, 0),
                Seat(23, 0),
                Seat(24, 0),

                Seat(25, 0),
                Seat(26, 0),
                Seat(0, 0),
                Seat(27, 0),
                Seat(28, 0),

                Seat(29, 2),
                Seat(30, 0),
                Seat(0, 0),
                Seat(31, 0),
                Seat(32, 2),

                Seat(33, 0),
                Seat(34, 0),
                Seat(0, 0),
                Seat(35, 0),
                Seat(36, 0),

                Seat(37, 0),
                Seat(38, 0),
                Seat(0, 0),
                Seat(39, 0),
                Seat(40, 0),

                Seat(41, 0),
                Seat(42, 0),
                Seat(0, 0),
                Seat(43, 0),
                Seat(44, 0),
        )
        b.recyclerBookSeats.adapter = BookSeatAdapter(this, seats)
        b.recyclerBookSeats.layoutManager = GridLayoutManager(context, 5)
    }

    // Display how many seats the user has selected
    fun onSeatSelected(counter: Int) {
        if (counter == 0) {
            b.textViewCounter.text = getString(R.string.please_select_a_seat)
        } else {
            b.textViewCounter.text = getString(R.string.book_seats, counter)
        }
        val bookFragment = parentFragment as BookFragment
        bookFragment.buttonNextVisibility(counter != 0)
        bookFragment.seatCounter = counter
    }
}