package com.example.beercatalogue.data.remote.paging.sources

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.data.common.repository.Repository
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BeerCatalogueRemoteMediator @Inject constructor(
    private val repository: Repository
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = repository.remote.getBeersCatalogue(currentPage, 25)
            val endOfPaginationReached = response.body()?.size!! < state.config.pageSize


            val prevPage = if (currentPage == 1) null else currentPage.minus(1)
            val nextPage = if (endOfPaginationReached) null else currentPage.plus(1)

            repository.local.storeBeerCatalogueAndRemoteKeysTransaction(
                response.body()!!,
                prevPage,
                nextPage,
                loadType
            )

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(state: PagingState<Int, BeerEntity>): BeerRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                repository.local.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, BeerEntity>): BeerRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { beer ->
                repository.local.getRemoteKeys(beer.id)
            }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, BeerEntity>): BeerRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { beer ->
                repository.local.getRemoteKeys(beer.id)
            }
    }
}