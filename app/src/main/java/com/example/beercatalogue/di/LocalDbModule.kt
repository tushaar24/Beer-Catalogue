package com.example.beercatalogue.di

import android.content.Context
import androidx.room.Room
import com.example.beercatalogue.data.local.database.BeerCatalogueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        BeerCatalogueDatabase::class.java,
        "beerCatalogue"
    ).build()
}