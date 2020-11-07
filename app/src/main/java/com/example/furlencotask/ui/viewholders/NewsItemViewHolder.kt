package com.example.furlencotask.ui.viewholders

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furlencotask.R
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import kotlinx.android.synthetic.main.new_item_layout.view.*

/**
 * Created by Sourik on 5/11/20.
 */

class NewsItemViewHolder(
    itemView: View,
    private val onItemClick: (String?) -> Unit,
    private val onFavouriteIconClicked: (DBNewsEntity) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    fun setData(entity: DBNewsEntity) {
        entity.let {
            Glide.with(itemView.context)
                .load(it.imageUrl)
                .placeholder(getShimmerDrawable())
                .into(itemView.iv_cover_image)
            itemView.tv_title.text = it.title
            itemView.tv_description.text = it.description
            itemView.tv_author.text = it.author
            if (it.isFavourite)
                itemView.iv_favourite.setImageResource(R.drawable.ic_favorite_filled)
            else
                itemView.iv_favourite.setImageResource(R.drawable.ic_favorite_border)

            itemView.setOnClickListener { _ ->
                onItemClick.invoke(it.newsUrl)
            }

            itemView.iv_favourite.setOnClickListener { _ ->
                onFavouriteIconClicked.invoke(it)
            }
        }
    }

    private fun getShimmerDrawable(): Drawable {
        return ShimmerDrawable().apply {
            setShimmer(
                Shimmer.AlphaHighlightBuilder().setDuration(1800).setBaseAlpha(0.9f)
                    .setHighlightAlpha(0.8f).setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true).build()
            )
        }
    }
}