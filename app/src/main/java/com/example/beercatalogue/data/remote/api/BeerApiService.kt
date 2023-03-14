package com.example.beercatalogue.data.remote.api

import com.example.beercatalogue.data.common.entity.BeerEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {
    @GET("v2/beers")
    suspend fun getBeersCatalogue(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<BeerEntity>>
}