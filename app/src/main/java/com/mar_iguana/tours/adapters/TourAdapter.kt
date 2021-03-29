package com.mar_iguana.tours.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.TourItemBinding
import com.mar_iguana.tours.models.Tour
import java.text.NumberFormat
import java.util.*

class TourAdapter(val tours: ArrayList<Tour>) : RecyclerView.Adapter<TourAdapter.MyViewHolder>() {
    private lateinit var binding: TourItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tour_item, parent, false)
        binding = TourItemBinding.bind(itemView)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindTour(tours[position])
    }

    override fun getItemCount(): Int {
        return tours.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTour(tour: Tour) {
            binding.imageViewPortrait.setImageResource(tour.images[0])
            binding.textViewTitle.text = tour.title
            binding.textViewDates.text = tour.dates
            binding.textViewPrice.text = formatPrice(tour.price)
            binding.ratingBar.rating = tour.rating
        }
    }

    fun formatPrice(number: Float) : String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("MXN")
        return format.format(number)
    }
}
