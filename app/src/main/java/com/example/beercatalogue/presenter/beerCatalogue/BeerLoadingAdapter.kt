package com.example.beercatalogue.presenter.beerCatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beercatalogue.databinding.ItemProgressBarBinding

class BeerLoadingAdapter : LoadStateAdapter<BeerLoadingAdapter.BeerLoadViewHolder>() {
    class BeerLoadViewHolder(
        private val binding: ItemProgressBarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            loadState: LoadState
        ) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }

        companion object {
            fun from(parent: ViewGroup): BeerLoadViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProgressBarBinding.inflate(layoutInflater, parent, false)
                return BeerLoadViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BeerLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BeerLoadViewHolder {
        return BeerLoadViewHolder.from(parent)
    }
}