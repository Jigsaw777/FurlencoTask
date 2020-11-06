package com.example.furlencotask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furlencotask.R
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import com.example.furlencotask.ui.viewholders.NewsItemViewHolder

/**
 * Created by Sourik on 5/11/20.
 */

class NewsItemAdapter(
    private val onItemClicked: (String?) -> Unit,
    private val onFavouriteItemClicked: (Boolean) -> Unit
) : RecyclerView.Adapter<NewsItemViewHolder>() {

    private var list = mutableListOf<DBNewsEntity>()

    fun setItems(items : List<DBNewsEntity>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.new_item_layout, parent, false),
            onItemClicked,
            onFavouriteItemClicked
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size

}