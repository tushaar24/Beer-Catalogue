package com.example.beercatalogue.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beercatalogue.data.common.entity.BeerEntity

@Dao
interface BeerCatalogueDao {
    @Query("SELECT * FROM BeerCatalogue")
    fun getBeerCatalogue(): PagingSource<Int, BeerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBeerCatalogue(beerCatalogue: List<BeerEntity>)

    @Query("DELETE FROM BeerCatalogue")
    suspend fun deleteBeerCatalogue()
}