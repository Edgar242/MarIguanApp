package com.mar_iguana.tours.webservices

import com.mar_iguana.tours.response.TourResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getTours(@Url url: String): Response<TourResponse>
}