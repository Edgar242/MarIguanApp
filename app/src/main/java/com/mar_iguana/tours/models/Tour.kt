package com.mar_iguana.tours.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tour(
    val id: Int,
    val title: String,
    val price: Int,
    val dates: String,
    val rating: Float,
    val images: List<Int>,
    val info: String,
    val itinerary: String,
    val promo:String?,
    val roomOptions: ArrayList<String>
): Parcelable