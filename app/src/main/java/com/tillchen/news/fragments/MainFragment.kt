package com.tillchen.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tillchen.news.adapters.NewsAdapter
import com.tillchen.news.data.NewsArticle
import com.tillchen.news.databinding.FragmentMainBinding
import com.tillchen.news.viewmodels.MainFragmentViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private val viewModel: MainFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false,
        )
        setUpRecyclerView()
        viewModel.newsData.observe(viewLifecycleOwner) { articles ->
            newsAdapter.addData(articles)
        }
        return binding.root
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(emptyList()) {
            navigateToNewsDetailFragment(it)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Ignore scroll up for API calls
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Pagination. Load more when we reach the end of the list.
                if (totalItemCount == lastVisibleItem + 1 && !viewModel.isLoading) {
                    viewModel.fetchNewsData()
                }
            }
        })
    }

    private fun navigateToNewsDetailFragment(newsArticle: NewsArticle) {
        val action = MainFragmentDirections.actionMainFragmentToNewsDetailFragment(newsArticle)
        findNavController().navigate(action)
    }
}
