package com.example.furlencotask.ui.activities

/**
 * Created by Sourik on 5/11/20.
 */

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.furlencotask.R
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.ui.adapters.ViewPagerAdapter
import com.example.furlencotask.ui.fragments.FavouritesBaseFragment
import com.example.furlencotask.ui.fragments.OnItemClick
import com.example.furlencotask.ui.fragments.ShowNewsFragment
import com.example.furlencotask.ui.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClick {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewsAndListeners()
    }

    private fun initViewsAndListeners() {
        vp_fragments.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tab_layout, vp_fragments) { tab, position ->
            tab.text = RequestType.values()[position].toString()
        }.attach()

        btn_favourite.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FavouritesBaseFragment.newInstance())
                .addToBackStack(null).commit()
        }

        btn_clear_cache.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(AppConstants.ALERT_TITLE)
                .setMessage(AppConstants.ALERT_MESSAGE)
                .setPositiveButton(
                    AppConstants.POSITIVE
                ) { _, _ ->
                    mainViewModel.clearAllData()
                }
                .setNegativeButton(
                    AppConstants.NEGATIVE
                ) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        mainViewModel.finishedClearingDB.observe(this, {
            Toast.makeText(this, "Data has been cleared", Toast.LENGTH_SHORT).show()
            finish()
            overridePendingTransition(0,0)
            startActivity(intent)
            overridePendingTransition(0,0)
        })
    }

    override fun onClick(url: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ShowNewsFragment.newInstance(url))
            .addToBackStack(null).commitAllowingStateLoss()
    }
}