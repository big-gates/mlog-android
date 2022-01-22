package com.kychan.mlog.presentation

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kychan.mlog.R
import com.kychan.mlog.databinding.DialogMovieBinding
import com.kychan.mlog.ext.setImage
import com.kychan.mlog.presentation.main.mypage.MyMovieItem

class MovieDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_movie, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        with(binding) {
            val movieItem = arguments?.getSerializable(KEY_MOVIE_ITEM) as MyMovieItem

            movieImage.setImage(movieItem.image)
            title.text = movieItem.title
            director.text = movieItem.director
            actor.text = movieItem.actor
            pubDate.text = movieItem.pubDate
            userRating.text = getString(R.string.evaluation, "★") + "${movieItem.userRating}"
            movieInputRating.rating = movieItem.evaluation

            learnMoreMovieDetail.setOnClickListener {
                startWeb(movieItem.link)
            }

            movieInputRating.setOnRatingBarChangeListener { _, rating, _ ->
                setFragmentResult(
                    this@MovieDialog::class.java.simpleName,
                    bundleOf(RESULT_RATING to rating)
                )
                dismiss()
            }

            deleteMovie.setOnClickListener {
                setFragmentResult(
                    this@MovieDialog::class.java.simpleName,
                    bundleOf(RESULT_DELETE to Activity.RESULT_OK)
                )
                dismiss()
            }
        }
    }

    private fun startWeb(url: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(requireContext(), Uri.parse(url))
    }

    companion object {
        const val RESULT_DELETE = "result_delete"
        const val RESULT_RATING = "result_rating"
        private const val KEY_MOVIE_ITEM = "MOVIE_ITEM"
        fun newInstance(movieItem: MyMovieItem) =
            MovieDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_MOVIE_ITEM, movieItem)
                }
            }
    }
}
