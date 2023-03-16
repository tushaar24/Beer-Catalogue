package com.example.beercatalogue.data.remote.paging.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.data.common.repository.RepositoryImpl
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys
import com.example.beercatalogue.utils.Constant.DEFAULT_PAGE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BeerCatalogueRemoteMediator @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun initialize(): InitializeAction {
        val remoteKeys = repositoryImpl?.local?.getRemoteKeys(1)?.lastTimeUpdated ?: 0
        val cacheTimeout = 1440

        val cacheExpired = (System.currentTimeMillis() - remoteKeys) > (cacheTimeout * 60 * 1000)

        return if (cacheExpired) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
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
            val response = repositoryImpl.remote.getBeersCatalogue(currentPage, 25)
            val endOfPaginationReached = response.body()?.size!! < state.config.pageSize

            val prevPage = if (currentPage == 1) null else currentPage.minus(1)
            val nextPage = if (endOfPaginationReached) null else currentPage.plus(1)

            repositoryImpl.local.storeBeerCatalogueAndRemoteKeysTransaction(
                response.body()!!,
                prevPage,
                nextPage,
                loadType,
                System.currentTimeMillis()
            )

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, BeerEntity>): BeerRemoteKeys? {
        return state.anchorPosition?.let { remoteKey ->
            state.closestItemToPosition(remoteKey)?.let {
                repositoryImpl.local.getRemoteKeys(it.id)
            }
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, BeerEntity>): BeerRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { beer ->
                repositoryImpl.local.getRemoteKeys(beer.id)
            }
    }
}