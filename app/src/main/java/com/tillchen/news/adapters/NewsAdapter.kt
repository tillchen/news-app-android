package com.tillchen.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tillchen.news.data.NewsArticle
import com.tillchen.news.databinding.ItemNewsBinding

class NewsAdapter(
    private var newsList: List<NewsArticle>,
    private val onItemClicked: (item: NewsArticle) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsArticle = newsList[position]
        with(holder.binding) {
            textViewTitle.text = newsArticle.title
            textViewDate.text = newsArticle.publishedAt
            Glide.with(imageViewThumbnail.context).load(newsArticle.urlToImage).into(imageViewThumbnail)
            root.setOnClickListener {
                onItemClicked(newsArticle)
            }
        }
    }

    override fun getItemCount() = newsList.size

    fun addData(newNewsList: List<NewsArticle>) {
        val oldSize = itemCount
        newsList = newNewsList
        notifyItemRangeInserted(oldSize, itemCount)
    }
}
