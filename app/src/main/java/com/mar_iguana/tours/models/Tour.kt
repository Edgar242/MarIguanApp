package com.mar_iguana.tours.models

data class Tour(
    val id: Int,
    val title: String,
    val price: Float,
    val dates: String,
    val rating: Float,
    val images: List<Int>,
)
