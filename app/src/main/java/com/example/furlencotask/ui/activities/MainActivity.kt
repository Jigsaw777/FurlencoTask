package com.example.furlencotask.ui.activities

/**
 * Created by Sourik on 5/11/20.
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.furlencotask.R
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.ui.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vp_fragments.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tab_layout, vp_fragments) { tab, position ->
            tab.text = RequestType.values()[position].toString()
        }.attach()
    }
}