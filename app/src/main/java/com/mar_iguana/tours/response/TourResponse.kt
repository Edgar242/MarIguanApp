package com.mar_iguana.tours.response

import com.google.gson.annotations.SerializedName
import com.mar_iguana.tours.models.Tour

data class TourResponse (
    @SerializedName("tours") var tours: ArrayList<Tour>
)