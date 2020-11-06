package com.example.furlencotask.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.ui.activities.MainActivity
import com.example.furlencotask.ui.fragments.GenericViewListFragment

/**
 * Created by Sourik on 5/11/20.
 */

class ViewPagerAdapter(private val fm: MainActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = RequestType.values().size

    override fun createFragment(position: Int): Fragment {
        val fragment = GenericViewListFragment.newInstance(position)
        fragment.setReference(fm)
        return fragment
    }
}