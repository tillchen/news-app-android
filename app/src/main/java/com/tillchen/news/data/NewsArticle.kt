package com.tillchen.news.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    val title: String,
    val author: String?,
    var publishedAt: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val source: NewsSource?,
) : Parcelable

@Parcelize
data class NewsSource(val name: String?) : Parcelable
