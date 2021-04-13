package com.mar_iguana.tours.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mar_iguana.tours.R
import com.mar_iguana.tours.databinding.TourItemBinding
import com.mar_iguana.tours.listeners.TourListener
import com.mar_iguana.tours.models.Tour
import java.text.NumberFormat
import java.util.*

class TourAdapter(val tours: ArrayList<Tour>) : RecyclerView.Adapter<TourAdapter.MyViewHolder>() {
    private var tourListener: TourListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tour_item, parent, false)
        val tourViewHolder = MyViewHolder(itemView)
        tourViewHolder.setTourListener(tourListener)
        return tourViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindTour(tours[position])
    }

    override fun getItemCount(): Int {
        return tours.size
    }

    fun setTourListener(listener: TourListener?){
        this.tourListener=listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = TourItemBinding.bind(itemView)
        private var tourListener: TourListener? = null
        private var cardTour: CardView = itemView.findViewById(R.id.cardViewProduct)

        fun bindTour(tour: Tour) {
            binding.imageViewPortrait.setImageResource(tour.images[0])
            binding.textViewTitle.text = tour.title
            binding.textViewDates.text = tour.dates
            binding.textViewPrice.text = formatPrice(tour.price)
            binding.ratingBar.rating = tour.rating
            binding.cardViewProduct.setOnClickListener{
                tourListener?.onClickShowTourDetail(tour)
            }
        }

        fun setTourListener(listener: TourListener?){
            this.tourListener=listener
        }
    }

    fun formatPrice(number: Float) : String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("MXN")
        return format.format(number)
    }
}
