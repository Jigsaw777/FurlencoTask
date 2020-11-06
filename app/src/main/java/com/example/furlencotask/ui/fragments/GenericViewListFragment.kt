package com.example.furlencotask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.furlencotask.R
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.ui.adapters.NewsItemAdapter
import com.example.furlencotask.ui.viewmodels.GenericViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_generic_view_list.*

interface OnItemClick {
    fun onClick(url: String)
}

@AndroidEntryPoint
class GenericViewListFragment : Fragment() {

    private var params: Int? = null

    private lateinit var adapter: NewsItemAdapter

    private lateinit var onItemClick: OnItemClick

    private val viewModel: GenericViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generic_view_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        params = arguments?.getInt(AppConstants.REQUEST_TYPE_POSITION)

        adapter = NewsItemAdapter({
            showNews(it ?: "")
        }, {
            viewModel.toggleFavouriteValue(it)
        })
        rv_news.layoutManager = LinearLayoutManager(context)
        (rv_news.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        rv_news.adapter = adapter

        viewModel.newsResultsLD.observe(viewLifecycleOwner, {
            adapter.setItems(it)
        })

        viewModel.listEmptyLD.observe(viewLifecycleOwner, {
            if (it)
                viewModel.getNews(params ?: 0)
            else
                viewModel.fetchNewsFromLocal(params ?: 0)
        })

        viewModel.changeFavouriteLD.observe(viewLifecycleOwner, {
            adapter.updateItem(it)
        })

        viewModel.isTableEmpty(params ?: 0)
    }

    fun setReference(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    private fun showNews(newsUrl: String) {
        onItemClick.onClick(newsUrl)
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            GenericViewListFragment().apply {
                arguments = Bundle().apply {
                    putInt(AppConstants.REQUEST_TYPE_POSITION, position)
                }
            }
    }
}