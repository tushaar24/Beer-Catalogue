package com.example.beercatalogue.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys

@Dao
interface BeerCatalogueRemoteKeysDao {
    @Query("SELECT * FROM BeerRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): BeerRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(beerRemoteKeys: List<BeerRemoteKeys>)

    @Query("DELETE FROM BeerRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}