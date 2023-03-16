package com.example.beercatalogue.domain.repository

import androidx.paging.PagingData
import com.example.beercatalogue.data.common.entity.BeerEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getCachedBeerCatalogue(): Flow<PagingData<BeerEntity>>
}