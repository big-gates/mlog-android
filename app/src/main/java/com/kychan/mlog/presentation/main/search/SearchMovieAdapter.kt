package com.kychan.mlog.presentation.main.search

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

class SearchMovieAdapter(
    private val itemClick: (SearchMovieItem) -> Unit,
    private val bookmarkClick: (SearchMovieItem) -> Unit
) :
    PagedListAdapter<SearchMovieItem, SearchMovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder =
        SearchMovieViewHolder(parent, itemClick, bookmarkClick)

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val searchMovieItem: SearchMovieItem? = getItem(position)
        if (searchMovieItem != null) {
            holder.bind(searchMovieItem)
        }
    }

    override fun onBindViewHolder(
        holder: SearchMovieViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains(CHECK_ACTIVATE)) {
            val searchMovieItem: SearchMovieItem? = getItem(position)
            if (searchMovieItem != null) {
                holder.setBookmarkImage(searchMovieItem.isMyMovie)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    companion object {
        private const val CHECK_ACTIVATE = "check_activate"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchMovieItem>() {
            override fun getChangePayload(oldItem: SearchMovieItem, newItem: SearchMovieItem): Any? {
                if (oldItem.isMyMovie != newItem.isMyMovie) return CHECK_ACTIVATE

                return super.getChangePayload(oldItem, newItem)
            }
            override fun areItemsTheSame(oldItem: SearchMovieItem, newItem: SearchMovieItem): Boolean =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: SearchMovieItem, newItem: SearchMovieItem): Boolean =
                oldItem == newItem
        }
    }
}
