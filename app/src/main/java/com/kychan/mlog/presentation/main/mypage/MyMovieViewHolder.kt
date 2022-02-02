package com.kychan.mlog.presentation.main.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kychan.mlog.R
import com.kychan.mlog.databinding.ItemMyMovieBinding
import com.kychan.mlog.ext.setImage

class MyMovieViewHolder(
    parent: ViewGroup,
    private val itemClick: (MyMovieItem) -> Unit,
    private val binding: ItemMyMovieBinding =
        ItemMyMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MyMovieItem) {
        with(binding) {
            root.setOnClickListener {
                itemClick(item)
            }
            movieImage.setImage(item.image)
            title.text = item.title
            evaluationText.run {
                text = if (item.evaluation == 0f) {
                    setTextColor(context.getColor(R.color.colorPrimaryDark))
                    resources.getString(R.string.evaluation_false, "★") + "${item.evaluation}"
                } else {
                    setTextColor(context.getColor(R.color.theme_FDA043))
                    resources.getString(R.string.evaluation_true, "★") + "${item.evaluation}"
                }
            }
        }
    }
}
