package com.example.beercatalogue.data.local.repository

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.data.local.database.BeerCatalogueDatabase
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys
import javax.inject.Inject

class LocalDbRepository @Inject constructor(
    private val beerCatalogueDatabase: BeerCatalogueDatabase
) {
    private val beerCatalogueDao = beerCatalogueDatabase.getBeerCatalogueDao()
    private val beerCatalogueRemoteKeysDao = beerCatalogueDatabase.getBeerCatalogueRemoteKeysDao()

    fun getBeerCatalogue(): PagingSource<Int, BeerEntity> {
        return beerCatalogueDao.getBeerCatalogue()
    }

    private suspend fun addBeerCatalogue(beerCatalogue: List<BeerEntity>) {
        beerCatalogueDao.addBeerCatalogue(beerCatalogue)
    }

    private suspend fun deleteBeerCatalogue() {
        beerCatalogueDao.deleteBeerCatalogue()
    }

    suspend fun getRemoteKeys(id: Int): BeerRemoteKeys {
        return beerCatalogueRemoteKeysDao.getRemoteKeys(id)
    }

    private suspend fun addAllRemoteKeys(beerRemoteKeys: List<BeerRemoteKeys>) {
        beerCatalogueRemoteKeysDao.addAllRemoteKeys(beerRemoteKeys)
    }

    private suspend fun deleteRemoteKeys() {
        beerCatalogueRemoteKeysDao.deleteAllRemoteKeys()
    }

    suspend fun storeBeerCatalogueAndRemoteKeysTransaction(
        beerCatalogue: List<BeerEntity>,
        prevPage: Int?,
        nextPage: Int?,
        loadType: LoadType
    ) {

        try {
            beerCatalogueDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    deleteBeerCatalogue()
                    deleteRemoteKeys()
                }

                Log.d("oxoxtushar", "database: ${beerCatalogue.get(1).id}")

                addBeerCatalogue(beerCatalogue)
                val keys = beerCatalogue.map { beer ->
                    BeerRemoteKeys(
                        id = beer.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                addAllRemoteKeys(keys)
            }
        } catch (e: Exception) {
            Log.d("oxoxtushar", "something went wrong")
        }
    }
}