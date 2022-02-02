package com.kychan.mlog.presentation.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kychan.mlog.R
import com.kychan.mlog.databinding.ItemMovieBinding
import com.kychan.mlog.ext.setImage

class SearchMovieViewHolder(
    parent: ViewGroup,
    private val itemClick: (SearchMovieItem) -> Unit,
    private val bookmarkClick: (SearchMovieItem) -> Unit,
    private val binding: ItemMovieBinding =
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchMovieItem) {
        with(binding) {
            root.setOnClickListener {
                itemClick(item)
            }
            bookmark.setOnClickListener {
                bookmarkClick(item)
                item.isMyMovie = !item.isMyMovie
                setBookmarkImage(item.isMyMovie)
            }
            movieImage.setImage(item.image)
            title.text = item.title
            director.text = item.director
            actor.text = item.actor
            pubDate.text = item.pubDate
            userRating.rating = item.userRating / 2

            setBookmarkImage(item.isMyMovie)
        }
    }

    fun setBookmarkImage(isMyMovie: Boolean) {
        if (isMyMovie) {
            binding.bookmark.setImageResource(R.drawable.ic_bookmark)
        } else {
            binding.bookmark.setImageResource(R.drawable.ic_bookmark_border)
        }
    }
}
