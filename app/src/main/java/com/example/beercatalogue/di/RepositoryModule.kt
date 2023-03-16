package com.example.beercatalogue.di

import com.example.beercatalogue.data.common.repository.RepositoryImpl
import com.example.beercatalogue.data.local.sources.LocalDbSourceImpl
import com.example.beercatalogue.data.remote.sources.BeerApiSourceImpl
import com.example.beercatalogue.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        local: LocalDbSourceImpl,
        remote: BeerApiSourceImpl
    ): Repository {
        return RepositoryImpl(local, remote)
    }

}