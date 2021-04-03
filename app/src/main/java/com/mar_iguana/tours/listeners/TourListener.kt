package com.mar_iguana.tours.listeners

import com.mar_iguana.tours.models.Tour

interface TourListener {
    fun onClickShowTourDetail(tour: Tour)
}