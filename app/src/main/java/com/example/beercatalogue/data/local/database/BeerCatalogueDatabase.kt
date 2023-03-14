package com.example.beercatalogue.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beercatalogue.data.local.dao.BeerCatalogueDao
import com.example.beercatalogue.data.local.dao.BeerCatalogueRemoteKeysDao
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.data.local.entity.BeerRemoteKeys

@Database(
    entities = [BeerEntity::class, BeerRemoteKeys::class],
    version = 1
)
abstract class BeerCatalogueDatabase : RoomDatabase() {
    abstract fun getBeerCatalogueDao(): BeerCatalogueDao
    abstract fun getBeerCatalogueRemoteKeysDao(): BeerCatalogueRemoteKeysDao
}