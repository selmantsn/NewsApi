package com.newsapi.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.newsapi.R
import com.newsapi.databinding.ItemLoadStateBinding

class ItemLoadStateAdapter : LoadStateAdapter<ItemLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent)

    inner class LoadStateViewHolder(
        parent: ViewGroup,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
    ) {
        private val binding = ItemLoadStateBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.progressBar

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}