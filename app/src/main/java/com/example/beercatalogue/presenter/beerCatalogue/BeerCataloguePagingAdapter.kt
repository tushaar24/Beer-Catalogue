package com.example.beercatalogue.presenter.beerCatalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beercatalogue.Application
import com.example.beercatalogue.R
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.databinding.ItemBeerBinding
import com.squareup.picasso.Picasso

class BeerCataloguePagingAdapter(
    private val onClick: (beerEntity: BeerEntity) -> Unit,
    private val context: Context
) : PagingDataAdapter<BeerEntity, BeerCataloguePagingAdapter.BeerCatalogueViewHolder>(DiffUtil) {

    val isDataSetChanged = MutableLiveData(false)

    class BeerCatalogueViewHolder(
        private val binding: ItemBeerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            beer: BeerEntity,
            onClick: (beerEntity: BeerEntity) -> Unit,
            context: Context,
            position: Int,
            itemCount: Int
        ) {
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_placeholder_beer, context.theme)?.let {
                Picasso.get()
                    .load(beer.imageURL)
                    .placeholder(it)
                    .into(binding.ivBeerImage)
            }

            binding.tvBeerName.text = beer.name

            binding.root.setOnClickListener {
                onClick(beer)
            }

            var top = dpToPx(24, context)
            var bottom = dpToPx(24, context)
            var right = dpToPx(12, context)
            var left = dpToPx(12, context)

            val spanCount = 2

            val isFirst2Items = position < spanCount
            val islast2Items = position > itemCount - spanCount

            if(islast2Items){
                bottom = dpToPx(24, context)
            }

            if(isFirst2Items){
                top = dpToPx(24, context)
            }

            val isLeftSide = (position + 1) % spanCount != 0
            val isRightSide = !isLeftSide

            if(isLeftSide){
                right = dpToPx(12, context)
            }

            if(isRightSide){
                left = dpToPx(12, context)
            }

            val layoutParams = ConstraintLayout.LayoutParams(binding.clLayout.layoutParams)
            layoutParams.setMargins(left, top, right, bottom)
            binding.clLayout.layoutParams = layoutParams
        }

        private fun dpToPx(dp: Int, context: Context): Int{
            val px = dp * context.resources.displayMetrics.density
            return px.toInt()
        }

        companion object {
            fun from(parent: ViewGroup): BeerCatalogueViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBeerBinding.inflate(layoutInflater, parent, false)
                return BeerCatalogueViewHolder(binding)
            }
        }
    }


    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<BeerEntity>() {
        override fun areItemsTheSame(oldItem: BeerEntity, newItem: BeerEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BeerEntity, newItem: BeerEntity): Boolean {
            return oldItem.id  == newItem.id
        }

    }

    override fun onViewAttachedToWindow(holder: BeerCatalogueViewHolder) {
        super.onViewAttachedToWindow(holder)
        isDataSetChanged.value = true
    }

    override fun onBindViewHolder(holder: BeerCatalogueViewHolder, position: Int) {
        val currentBeerData = getItem(position)
        if (currentBeerData != null) {
            holder.bind(currentBeerData, onClick, context, position, itemCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerCatalogueViewHolder {
        return BeerCatalogueViewHolder.from(parent)
    }
}