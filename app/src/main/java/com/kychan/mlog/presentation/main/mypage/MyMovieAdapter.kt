package com.kychan.mlog.presentation.main.mypage

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

class MyMovieAdapter(private val itemClick: (MyMovieItem) -> Unit) :
    PagedListAdapter<MyMovieItem, MyMovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder =
        MyMovieViewHolder(parent, itemClick)

    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) {
        val movieItem: MyMovieItem? = getItem(position)
        if (movieItem != null) {
            holder.bind(movieItem)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MyMovieItem>() {
            override fun areItemsTheSame(oldItem: MyMovieItem, newItem: MyMovieItem): Boolean =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: MyMovieItem, newItem: MyMovieItem): Boolean =
                oldItem == newItem
        }
    }
}
