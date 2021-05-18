package com.mar_iguana.tours.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.BookSeatItemBinding
import com.mar_iguana.tours.models.Seat
import com.mar_iguana.tours.ui.home.booking.BookStepOneFragment

private const val SEAT_AVAILABLE = 0
private const val SEAT_SELECTED = 1
private const val SEAT_NOT_AVAILABLE = 2
private const val TYPE_BLANK = 0
private const val TYPE_DRIVER = -1

class BookSeatAdapter(val listener: BookStepOneFragment, private val seats : ArrayList<Seat>)
    : RecyclerView.Adapter<BookSeatAdapter.MyViewHolder>() {
    private var counter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
                        .inflate(R.layout.book_seat_item, parent, false)
        return MyViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindBookSeat(seats[position])
    }

    override fun getItemCount(): Int {
        return seats.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BookSeatItemBinding.bind(itemView)

        fun bindBookSeat(seat: Seat) {
            drawSeats(seat)

            binding.imageButtonSeat.setOnClickListener {
                toggleSeat(seat)
            }
        }

        // Draw empty spaces, driver steering wheel and seats with its color and number
        private fun drawSeats(seat: Seat) {
            if (seat.number == TYPE_BLANK) {
                binding.textViewSeatNumber.text = ""
                binding.imageButtonSeat.visibility = View.INVISIBLE
                return
            }

            if (seat.number == TYPE_DRIVER) {
                binding.textViewSeatNumber.text = ""
                binding.imageButtonSeat.setImageResource(R.drawable.steering)
                binding.imageButtonSeat.rotation = 0F
                binding.imageButtonSeat.isEnabled = false
                return
            }

            // Set number and color of seats
            binding.textViewSeatNumber.text = seat.number.toString()
            binding.imageButtonSeat.setImageResource(R.drawable.ic_seat_available)

            if (seat.status == SEAT_NOT_AVAILABLE) {
                binding.imageButtonSeat.setImageResource(R.drawable.ic_seat_not_available)
                binding.imageButtonSeat.isEnabled = false
            }
        }

        // Toggle image, status and counter when user select/deselect a seat
        private fun toggleSeat(seat: Seat) {
            when(seat.status) {
                SEAT_AVAILABLE -> {
                    binding.imageButtonSeat.setImageResource(R.drawable.ic_seat_selected)
                    seat.status = SEAT_SELECTED
                    counter++
                }
                SEAT_SELECTED -> {
                    binding.imageButtonSeat.setImageResource(R.drawable.ic_seat_available)
                    counter--
                    seat.status = SEAT_AVAILABLE
                }
            }
            listener.onSeatSelected(counter)
        }
    }
}