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
    private val onFavouriteItemClicked: (DBNewsEntity) -> Unit
) : RecyclerView.Adapter<NewsItemViewHolder>() {

    private var list = mutableListOf<DBNewsEntity>()

    fun setItems(items: List<DBNewsEntity>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItem(pair: Pair<String, Boolean>) {
        val item = list.firstOrNull { element -> element.newsUrl == pair.first }
        if (item != null) {
            val updatedItem = DBNewsEntity(
                author = item.author,
                title = item.title,
                description = item.description,
                newsUrl = item.newsUrl,
                imageUrl = item.imageUrl,
                publishDateInMillis = item.publishDateInMillis,
                content = item.content,
                type = item.type,
                isFavourite = pair.second
            )
            val pos = list.indexOf(item)
            list.removeAt(pos)
            list.add(pos, updatedItem)
            notifyItemChanged(pos)
        }
    }

    fun removeItem(pair: Pair<String, Boolean>) {
        val item = list.firstOrNull { element -> element.newsUrl == pair.first }
        if (item != null) {
            if (!pair.second) {
                val pos = list.indexOf(item)
                list.removeAt(pos)
                notifyItemRemoved(pos)
            }
        }
    }

    fun isEmpty() : Boolean = list.isEmpty()

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