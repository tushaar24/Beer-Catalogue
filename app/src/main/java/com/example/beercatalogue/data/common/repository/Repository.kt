package com.example.beercatalogue.data.common.repository

import androidx.paging.*
import com.example.beercatalogue.data.local.repository.LocalDbRepository
import com.example.beercatalogue.data.remote.paging.sources.BeerCatalogueRemoteMediator
import com.example.beercatalogue.data.remote.repository.NetworkRepository
import javax.inject.Inject

class Repository @Inject constructor(
    localDbRepository: LocalDbRepository,
    networkRepository: NetworkRepository
) {
    val local = localDbRepository
    val remote = networkRepository

    @OptIn(ExperimentalPagingApi::class)
    fun getCachedBeerCatalogue() = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = BeerCatalogueRemoteMediator(this),
        pagingSourceFactory = { local.getBeerCatalogue() }
    ).liveData
}