package com.kychan.mlog.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.kychan.mlog.R
import com.kychan.mlog.databinding.ActivityMainBinding
import com.kychan.mlog.presentation.main.mypage.MyPageFragment
import com.kychan.mlog.presentation.main.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.home_frg_container,
                SearchFragment.newInstance(),
                resources.getString(R.string.home)
            )
            .commit()

        setView()
        setToolbar(0)
        title = null
    }

    private fun setToolbar(position: Int) {
        supportActionBar?.run {
            when (position) {
                0 -> {
                    setDisplayShowCustomEnabled(true)
                }
                1 -> {
                    setDisplayShowCustomEnabled(false)
                }
            }
        }
    }

    private fun setView() {
        with(binding) {
            setSupportActionBar(toolbar)
            val studyTabList = resources.getStringArray(R.array.home_tab)
            studyTabList.forEach { title ->
                bottomTab.addTab(bottomTab.newTab().setText(title))
            }

            bottomTab.run {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        tab ?: return

                        setToolbar(tab.position)

                        val findFragment = supportFragmentManager.findFragmentByTag("${tab.text}")
                        if (findFragment == null) {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.home_frg_container, getFragment(tab.position), "${tab.text}")
                                .commit()
                        } else {
                            supportFragmentManager.beginTransaction()
                                .show(findFragment)
                                .commit()
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        tab ?: return
                        val hideFragment = supportFragmentManager.findFragmentByTag("${tab.text}")
                        if (hideFragment != null) {
                            supportFragmentManager.beginTransaction()
                                .hide(hideFragment)
                                .commit()
                        }
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })

                getTabAt(0)?.setIcon(R.drawable.ic_home)
                getTabAt(1)?.setIcon(R.drawable.ic_person)
            }
        }
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment.newInstance()
            1 -> MyPageFragment.newInstance()
            else -> error("Invalid position")
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.home_frg_container)

        when (fragment) {
            is MyPageFragment -> {
                if (binding.bottomTab.selectedTabPosition != 1) {
                    super.onBackPressed()
                } else {
                    if (fragment.onBackPressed()) {
                        super.onBackPressed()
                    }
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}
