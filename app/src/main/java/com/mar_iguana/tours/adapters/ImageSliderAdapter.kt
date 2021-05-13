package com.mar_iguana.tours.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mar_iguana.tours.R
import com.squareup.picasso.Picasso

class ImageSliderAdapter(private var images: List<String>)
    : RecyclerView.Adapter<ImageSliderAdapter.Pager2ViewHolder>()  {

    inner class Pager2ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        val itemImage: ImageView = itemView.findViewById(R.id.imageBar)

        init {
            itemImage.setOnClickListener{v :View ->
                val position:Int = adapterPosition
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSliderAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false))
    }

    override fun onBindViewHolder(holder: ImageSliderAdapter.Pager2ViewHolder, position: Int) {
        Picasso.get().load(images[position]).into(holder.itemImage)
        //holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}