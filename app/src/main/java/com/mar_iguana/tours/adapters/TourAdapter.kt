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
import com.mar_iguana.tours.utils.Utils
import com.squareup.picasso.Picasso
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
            val imagesTour: List<String> = tour.images
            Picasso.get().load(imagesTour[0]).into(binding.imageViewPortrait)
            binding.textViewTitle.text = tour.title
            val period = tour.dates[0] + " - " + tour.dates[1]
            binding.textViewDates.text = period
            binding.textViewPrice.text = Utils.localCurrencyFormat(tour.price)
            binding.ratingBar.rating = tour.rating
            binding.cardViewProduct.setOnClickListener{
                tourListener?.onClickShowTourDetail(tour)
            }
        }

        fun setTourListener(listener: TourListener?){
            this.tourListener=listener
        }
    }
}
