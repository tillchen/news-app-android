package com.tillchen.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tillchen.news.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsDetailBinding.inflate(
            inflater,
            container,
            false,
        )
        val args: NewsDetailFragmentArgs by navArgs()
        val newsArticle = args.newsArticle
        with (binding) {
            Glide.with(this@NewsDetailFragment).load(newsArticle.urlToImage).into(imageViewDetail)
            textViewTitleDetail.text = newsArticle.title
            textViewAuthorDetail.text = newsArticle.author
            textViewDateDetail.text = newsArticle.publishedAt
            textViewDescriptionDetail.text = newsArticle.description
            textViewSourceDetail.text = newsArticle.source?.name
            textViewLinkDetail.text = newsArticle.url
        }
        return binding.root
    }
}
