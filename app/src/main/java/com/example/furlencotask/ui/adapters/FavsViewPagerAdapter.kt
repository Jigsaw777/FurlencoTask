package com.example.furlencotask.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.ui.fragments.FavouritesBaseFragment
import com.example.furlencotask.ui.fragments.GenericFavsViewListFragment

/**
 * Created by Sourik on 7/11/20.
 */

class FavsViewPagerAdapter(private val fragment: FavouritesBaseFragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = RequestType.values().size

    override fun createFragment(position: Int): Fragment {
        val viewListFrag = GenericFavsViewListFragment.newInstance(position)
        viewListFrag.setReference(fragment)
        return viewListFrag
    }
}