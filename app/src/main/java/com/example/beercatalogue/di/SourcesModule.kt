package com.example.beercatalogue.di

import com.example.beercatalogue.data.local.database.BeerCatalogueDatabase
import com.example.beercatalogue.data.local.sources.LocalDbSourceImpl
import com.example.beercatalogue.data.remote.api.BeersApiService
import com.example.beercatalogue.data.remote.sources.BeerApiSourceImpl
import com.example.beercatalogue.domain.sources.BeerApiSource
import com.example.beercatalogue.domain.sources.LocalDbSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourcesModule {

    @Provides
    @Singleton
    fun provideLocalDbSource(
        beerCatalogueDatabase: BeerCatalogueDatabase
    ): LocalDbSource {
        return LocalDbSourceImpl(beerCatalogueDatabase)
    }

    @Provides
    @Singleton
    fun provideBeerApiSource(
        beersApiService: BeersApiService
    ): BeerApiSource {
        return BeerApiSourceImpl(beersApiService)
    }

}