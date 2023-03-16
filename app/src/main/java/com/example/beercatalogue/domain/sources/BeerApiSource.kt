package com.example.beercatalogue.domain.sources

import com.example.beercatalogue.data.common.entity.BeerEntity
import retrofit2.Response

interface BeerApiSource {
    suspend fun getBeersCatalogue(
        page: Int,
        perPage: Int
    ): Response<List<BeerEntity>>
}