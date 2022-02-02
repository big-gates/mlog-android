package com.kychan.mlog.presentation.main.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.kychan.mlog.R
import com.kychan.mlog.databinding.FragmentMyPageBinding
import com.kychan.mlog.presentation.main.MainActivity

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "영찬"
        childFragmentManager.beginTransaction()
            .replace(
                R.id.my_page_frg_container,
                MyMovieFragment.newInstance()
            )
            .commit()
    }

    fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.my_page_frg_container)
        Log.d("TAG", fragment.toString())
        when {
            fragment is MyMovieFragment -> activity?.finish()
            fragment != null -> {
                childFragmentManager.commitNow {
                    remove(fragment)
                }
                val myMovieFragment = childFragmentManager.fragments.find { it is MyMovieFragment }
                if (myMovieFragment != null) {
                    childFragmentManager.commit {
                        show(myMovieFragment)
                    }
                }
            }
            else -> {
                return true
            }
        }
        return false
    }

    companion object {
        fun newInstance() =
            MyPageFragment()
    }
}
