package com.example.furlencotask.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.furlencotask.R
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.ui.viewholders.NewsItemViewHolder

/**
 * Created by Sourik on 5/11/20.
 */

class NewsItemAdapter(
    private val onItemClicked: (String?) -> Unit,
    private val onFavouriteItemClicked: (Boolean) -> Unit
) : PagingDataAdapter<NewsModel.DBNewsEntity, NewsItemViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewsModel.DBNewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsModel.DBNewsEntity, newItem: NewsModel.DBNewsEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsModel.DBNewsEntity, newItem: NewsModel.DBNewsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.new_item_layout, parent, false),
            onItemClicked,
            onFavouriteItemClicked
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

}