package com.mar_iguana.tours.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tour(
    val id: Int,
    val title: String?,
    val price: Float,
    val dates: List<String>,
    val rating: Float,
    val images: List<String>,
    val info: String?,
    val itinerary: List<String>,
    val promo:String?,
    val urlInfoWeb:String?
): Parcelable