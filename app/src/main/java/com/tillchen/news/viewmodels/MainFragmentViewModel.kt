package com.tillchen.news.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tillchen.news.data.NewsArticle
import com.tillchen.news.network.NewsApiService
import com.tillchen.news.network.RetrofitInstance
import com.tillchen.news.util.getUserFriendlyDateFromISO
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragmentViewModel : ViewModel() {

    private val newsApiService: NewsApiService = RetrofitInstance.api
    private var currentPage: Int = 1
    private var lastFetchTime: Long = 0L

    private val _newsData: MutableLiveData<MutableList<NewsArticle>> = MutableLiveData()
    val newsData: LiveData<MutableList<NewsArticle>>
        get() = _newsData
    var isLoading = false

    init {
        fetchNewsData()
    }

    fun fetchNewsData() {
        val currentTime = System.currentTimeMillis()
        if (isLoading || currentTime - lastFetchTime < DEBOUNCE_TIME) return
        isLoading = true
        lastFetchTime = currentTime
        viewModelScope.launch {
            try {
                val response = newsApiService.getNews(API_KEY, currentPage, query = QUERY)
                Timber.d("fetchNewsData")
                if (response.isSuccessful && response.body() != null) {
                    val currentData = _newsData.value ?: mutableListOf()
                    val newArticles = response.body()!!.articles.map {
                        // Use the extension function to transform the date.
                        it.copy(publishedAt = it.publishedAt.getUserFriendlyDateFromISO())
                    }
                    currentData.addAll(newArticles)
                    _newsData.postValue(currentData)
                    currentPage++
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching news data")
            } finally {
                isLoading = false
            }
        }
    }

    companion object {
        private const val API_KEY: String = "85f71bb6de304384bd53966318abb20f"
        private const val QUERY: String = "tech"
        private const val DEBOUNCE_TIME: Int = 1000
    }
}
