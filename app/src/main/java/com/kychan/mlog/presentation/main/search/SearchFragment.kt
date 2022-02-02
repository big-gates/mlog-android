package com.kychan.mlog.presentation.main.search

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kychan.mlog.R
import com.kychan.mlog.databinding.FragmentSearchBinding
import com.kychan.mlog.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private val editText: EditText?
        get() = (activity as MainActivity).supportActionBar?.customView?.findViewById(R.id.edit_view) as? EditText
    private val clearButton: ImageView?
        get() = (activity as MainActivity).supportActionBar?.customView?.findViewById(R.id.clear_button) as? ImageView
    private val searchMovieAdapter by lazy {
        SearchMovieAdapter({
            startWeb(it.link)
        }, {
            if (it.isMyMovie) {
                searchViewModel.deleteMovie(it.link)
            } else {
                searchViewModel.insertMovie(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setViewModel()
    }

    private fun setView() {
        with(binding) {
            (activity as MainActivity).supportActionBar?.setCustomView(R.layout.view_search)

            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            editText?.apply {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (text.isNotEmpty()) {
                            searchViewModel.setKeyword(text.toString())
                            searchViewModel.getMovieList().observe(viewLifecycleOwner, { movieList ->
                                searchMovieAdapter.submitList(movieList)
                            })

                            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                            return@setOnEditorActionListener false
                        }
                    }
                    true
                }
                doOnTextChanged { text, _, _, _ ->
                    clearButton?.isInvisible = text.isNullOrEmpty()
                }
            }

            clearButton?.setOnClickListener {
                editText?.text?.clear()
                editText?.requestFocus()
                inputMethodManager
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }

            rvMovie.adapter = searchMovieAdapter
        }
    }

    private fun setViewModel() {
        with(searchViewModel) {
            total.observe(viewLifecycleOwner, {
                Log.d("TAG1234", "$it")
                binding.emptyView.isVisible = (it == null || it == 0)
                if (it == null || it == 0) {
                    binding.emptyView.text = getString(R.string.empty_search)
                }
            })
        }
    }

    private fun startWeb(url: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(requireContext(), Uri.parse(url))
    }

    companion object {
        fun newInstance() =
            SearchFragment()
    }
}
