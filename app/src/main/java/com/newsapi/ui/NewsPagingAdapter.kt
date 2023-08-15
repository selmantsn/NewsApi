package com.newsapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.newsapi.utils.DateFormat
import com.newsapi.model.response.Articles
import com.newsapi.databinding.NewsAdapterItemBinding
import com.newsapi.utils.loadImage
import com.newsapi.utils.toDate
import com.newsapi.utils.toString
import javax.inject.Inject

class NewsPagingAdapter @Inject constructor() :
    PagingDataAdapter<Articles, NewsPagingAdapter.ViewHolder>(DiffUtils) {

    interface Listener {
        fun itemClicked(url: String?)
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            NewsAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class ViewHolder(private val binding: NewsAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Articles) {
            binding.apply {
                tvTitle.text = item.title
                tvDescription.text = item.description
                ivImageUrl.loadImage(item.urlToImage)
                tvDate.text = item.publishedAt.toDate(DateFormat.API_FORMAT).toString(DateFormat.UI_FORMAT)
                root.setOnClickListener {
                    listener?.itemClicked(item.url)
                }
            }
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem == newItem
        }
    }

}