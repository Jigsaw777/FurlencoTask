package com.example.furlencotask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.furlencotask.R
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.ui.adapters.NewsItemAdapter
import com.example.furlencotask.ui.viewmodels.GenericViewModel
import com.example.furlencotask.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_generic_view_list.*

interface OnItemClick {
    fun onClick(url: String)
}

@AndroidEntryPoint
class GenericViewListFragment : Fragment() {

    private var params: Int? = null

    private lateinit var adapter: NewsItemAdapter
    private lateinit var manager: LinearLayoutManager
    private var isScrolling = false

    private lateinit var onItemClick: OnItemClick

    private val viewModel: GenericViewModel by viewModels()

    private val activityVM: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generic_view_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Generic fragment", "Fragment : $this  and view model : $viewModel")
        params = arguments?.getInt(AppConstants.REQUEST_TYPE_POSITION)
        initViews()
        initListeners()
        viewModel.setRowCount(params ?: 0)
    }

    private fun initViews() {
        adapter = NewsItemAdapter({
            showNews(it ?: "")
        }, {
            viewModel.toggleFavouriteValue(it)
        })
        manager = LinearLayoutManager(context)
        rv_news.layoutManager = manager
        (rv_news.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        rv_news.adapter = adapter
    }

    private fun initListeners(){
        viewModel.newsResultsLD.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                tv_blank_content.visibility = View.VISIBLE
            } else {
                viewModel.pageNumber += 1
                tv_blank_content.visibility = View.GONE
            }
            pb_loading.visibility = View.GONE
            adapter.setItems(it)
        })

        viewModel.listEmptyLD.observe(viewLifecycleOwner, {
            if (it)
                viewModel.getNews(
                    params ?: 0
                )
            else
                viewModel.fetchNewsFromLocal(params ?: 0)
        })

        viewModel.changeFavouriteLD.observe(viewLifecycleOwner, {
            adapter.updateItem(it)
        })

        activityVM.onChangedFav.observe(viewLifecycleOwner, {
            adapter.updateItem(it)
        })

        viewModel.successMsg.observe(viewLifecycleOwner, {
            Toast.makeText(context, "it", Toast.LENGTH_SHORT).show()
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, {
            Toast.makeText(context, "it", Toast.LENGTH_SHORT).show()
        })

        viewModel.rowCountSetLD.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.isTableEmpty(params ?: 0)
            } else
                Toast.makeText(context, AppConstants.VM_ERROR_MSG, Toast.LENGTH_SHORT).show()
        })

        rv_news.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling && (manager.childCount + manager.findFirstVisibleItemPosition() == manager.itemCount)) {
                    isScrolling = false
                    viewModel.getNews(
                        params ?: 0
                    )
                }
            }
        })
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