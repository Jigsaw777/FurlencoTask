package com.example.furlencotask.ui.activities

/**
 * Created by Sourik on 5/11/20.
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.furlencotask.R
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.ui.adapters.ViewPagerAdapter
import com.example.furlencotask.ui.fragments.FavouritesBaseFragment
import com.example.furlencotask.ui.fragments.OnItemClick
import com.example.furlencotask.ui.fragments.ShowNewsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_item_layout.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClick {

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
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, FavouritesBaseFragment.newInstance())
                .addToBackStack(null).commit()
        }
    }

    override fun onClick(url: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ShowNewsFragment.newInstance(url))
            .addToBackStack(null).commitAllowingStateLoss()
    }
}