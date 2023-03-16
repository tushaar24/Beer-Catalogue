package com.example.beercatalogue.domain.sources

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys

interface LocalDbSource {

    fun getBeerCatalogue(): PagingSource<Int, BeerEntity>

    suspend fun addBeerCatalogue(beerCatalogue: List<BeerEntity>)

    suspend fun deleteBeerCatalogue()

    suspend fun getRemoteKeys(id: Int): BeerRemoteKeys

    suspend fun addAllRemoteKeys(beerRemoteKeys: List<BeerRemoteKeys>)

    suspend fun deleteRemoteKeys()

    suspend fun storeBeerCatalogueAndRemoteKeysTransaction(
        beerCatalogue: List<BeerEntity>,
        prevPage: Int?,
        nextPage: Int?,
        loadType: LoadType,
        lastTimeUpdated: Long
    )
}