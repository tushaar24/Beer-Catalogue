package com.example.beercatalogue.data.remote.sources

import com.example.beercatalogue.data.remote.api.BeersApiService
import com.example.beercatalogue.domain.sources.BeerApiSource
import javax.inject.Inject

class BeerApiSourceImpl @Inject constructor(
    private val beersApiService: BeersApiService
) : BeerApiSource {

    override suspend fun getBeersCatalogue(
        page: Int,
        perPage: Int
    ) = beersApiService.getBeersCatalogue(page, perPage)

}