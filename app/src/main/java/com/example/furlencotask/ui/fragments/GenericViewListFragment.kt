package com.example.furlencotask.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furlencotask.R
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.ui.adapters.NewsItemAdapter
import com.example.furlencotask.ui.viewmodels.GenericViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_generic_view_list.*

@AndroidEntryPoint
class GenericViewListFragment : Fragment() {

    private var params: Int? = null

    private lateinit var adapter : NewsItemAdapter

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
        adapter= NewsItemAdapter({},{})
        rv_news.layoutManager = LinearLayoutManager(context)
        rv_news.adapter = adapter
        params = arguments?.getInt(AppConstants.REQUEST_TYPE_POSITION)

        adapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                it.error.printStackTrace()
                AlertDialog.Builder(view.context)
                    .setTitle("R.string.error")
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton("R.string.cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("R.string.retry") { _, _ ->
                        adapter.retry()
                    }
                    .show()
            }
        }

        viewModel.newsResultsLD.observe(viewLifecycleOwner,{
            adapter.submitData(lifecycle, it)
        })

        viewModel.getNews(params ?: 0)
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