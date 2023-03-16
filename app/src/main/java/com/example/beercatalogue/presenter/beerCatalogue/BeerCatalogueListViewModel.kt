package com.example.beercatalogue.presenter.beerCatalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BeerCatalogueListViewModel @Inject constructor (
    private val repository: Repository
): ViewModel() {

    fun getCachedBeerCatalogue(): Flow<PagingData<BeerEntity>>{
        return repository.getCachedBeerCatalogue()
    }

}