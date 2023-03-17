package com.example.beercatalogue.data.local.sources

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.data.local.database.BeerCatalogueDatabase
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys
import com.example.beercatalogue.domain.sources.LocalDbSource
import javax.inject.Inject

class LocalDbSourceImpl @Inject constructor(
    private val beerCatalogueDatabase: BeerCatalogueDatabase
) : LocalDbSource {
    private val beerCatalogueDao = beerCatalogueDatabase.getBeerCatalogueDao()
    private val beerCatalogueRemoteKeysDao = beerCatalogueDatabase.getBeerCatalogueRemoteKeysDao()

    override fun getBeerCatalogue(): PagingSource<Int, BeerEntity> {
        return beerCatalogueDao.getBeerCatalogue()
    }

    override suspend fun addBeerCatalogue(beerCatalogue: List<BeerEntity>) {
        beerCatalogueDao.addBeerCatalogue(beerCatalogue)
    }

    override suspend fun deleteBeerCatalogue() {
        beerCatalogueDao.deleteBeerCatalogue()
    }

    override suspend fun getRemoteKeys(id: Int): BeerRemoteKeys {
        return beerCatalogueRemoteKeysDao.getRemoteKeys(id)
    }

    override suspend fun addAllRemoteKeys(beerRemoteKeys: List<BeerRemoteKeys>) {
        beerCatalogueRemoteKeysDao.addAllRemoteKeys(beerRemoteKeys)
    }

    override suspend fun deleteRemoteKeys() {
        beerCatalogueRemoteKeysDao.deleteAllRemoteKeys()
    }

    override suspend fun storeBeerCatalogueAndRemoteKeysTransaction(
        beerCatalogue: List<BeerEntity>,
        prevPage: Int?,
        nextPage: Int?,
        loadType: LoadType,
        lastTimeUpdated: Long
    ) {
        try {
            beerCatalogueDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    deleteBeerCatalogue()
                    deleteRemoteKeys()
                }

                addBeerCatalogue(beerCatalogue)
                val keys = beerCatalogue.map { beer ->
                    BeerRemoteKeys(
                        id = beer.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        lastTimeUpdated = lastTimeUpdated
                    )
                }
                addAllRemoteKeys(keys)
            }
        } catch (e: Exception) {
        }
    }
}