package com.example.beercatalogue.data.common.repository

import androidx.paging.*
import com.example.beercatalogue.data.local.sources.LocalDbSourceImpl
import com.example.beercatalogue.data.remote.paging.sources.BeerCatalogueRemoteMediator
import com.example.beercatalogue.data.remote.sources.BeerApiSourceImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    localDbSourceImpl: LocalDbSourceImpl,
    beerApiSourceImpl: BeerApiSourceImpl
) {
    val local = localDbSourceImpl
    val remote = beerApiSourceImpl

    @OptIn(ExperimentalPagingApi::class)
    fun getCachedBeerCatalogue() = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        remoteMediator = BeerCatalogueRemoteMediator(this),
        pagingSourceFactory = { local.getBeerCatalogue() }
    ).liveData
}