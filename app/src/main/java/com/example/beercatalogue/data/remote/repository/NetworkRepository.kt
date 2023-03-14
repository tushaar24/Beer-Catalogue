package com.example.beercatalogue.data.remote.repository

import com.example.beercatalogue.data.remote.api.BeersApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val beersApiService: BeersApiService
) {
    suspend fun getBeersCatalogue(
        page: Int,
        perPage: Int
    ) = beersApiService.getBeersCatalogue(page, perPage)
}