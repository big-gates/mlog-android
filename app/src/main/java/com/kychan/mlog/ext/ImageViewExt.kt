package com.kychan.mlog.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kychan.mlog.R

fun ImageView.setImage(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .apply {
            centerCrop()
        }
        .error(R.drawable.empty_movie)
        .into(this)
}
